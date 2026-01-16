package se.alipsa.groovy.svg.utils

import groovy.transform.CompileStatic
import se.alipsa.groovy.svg.ElementContainer
import se.alipsa.groovy.svg.SvgElement

/**
 * CSS selector engine for querying SVG elements.
 * Supports common CSS selectors for element selection.
 *
 * @since 1.0.0
 */
@CompileStatic
class CssSelectorEngine {

    /**
     * Select all elements matching the given CSS selector.
     *
     * @param container the container to search in
     * @param selector the CSS selector
     * @return list of matching elements
     */
    static List<SvgElement> select(ElementContainer container, String selector) {
        if (!selector || selector.trim().isEmpty()) {
            return []
        }

        selector = selector.trim()

        // Handle child combinators (>) - must check before space since " > " contains space
        // Only treat as combinator if '>' is outside of attribute selector brackets
        if (hasChildCombinatorOutsideBrackets(selector)) {
            return selectWithChildCombinator(container, selector)
        }

        // Handle descendant combinators (space) - but not within brackets
        if (selector.contains(' ') && !isWithinBrackets(selector)) {
            return selectWithDescendantCombinator(container, selector)
        }

        // Simple selector - match against descendants
        return selectSimple(container, selector, true)
    }

    /**
     * Check if all spaces in the selector are within attribute selector brackets.
     * Returns false if any space is found outside brackets (indicating a combinator).
     */
    private static boolean isWithinBrackets(String selector) {
        boolean inBrackets = false
        for (int i = 0; i < selector.length(); i++) {
            char c = selector.charAt(i)
            if (c == '[') {
                inBrackets = true
            } else if (c == ']') {
                inBrackets = false
            } else if (c == ' ' && !inBrackets) {
                // Found a space outside brackets - this is a combinator
                return false
            }
        }
        // All spaces (if any) are within brackets
        return true
    }

    /**
     * Check if a child combinator ('>') exists outside of attribute selector brackets.
     * Returns true if '>' is found outside brackets, false otherwise.
     */
    private static boolean hasChildCombinatorOutsideBrackets(String selector) {
        boolean inBrackets = false
        for (int i = 0; i < selector.length(); i++) {
            char c = selector.charAt(i)
            if (c == '[') {
                inBrackets = true
            } else if (c == ']') {
                inBrackets = false
            } else if (c == '>' && !inBrackets) {
                // Found '>' outside brackets - this is a child combinator
                return true
            }
        }
        return false
    }

    /**
     * Check if a selector contains combinators (space or >).
     * Used to determine if recursive processing is needed.
     */
    private static boolean hasCombinator(String selector) {
        return (selector.contains(' ') && !isWithinBrackets(selector)) ||
               hasChildCombinatorOutsideBrackets(selector)
    }

    /**
     * Select first element matching the given CSS selector.
     *
     * @param container the container to search in
     * @param selector the CSS selector
     * @return first matching element or null
     */
    static SvgElement selectFirst(ElementContainer container, String selector) {
        List<SvgElement> results = select(container, selector)
        return results.isEmpty() ? null : results[0]
    }

    /**
     * Select elements with descendant combinator (e.g., "g circle").
     *
     * Note: This method splits the selector on the first space, handling only two parts.
     * Chained combinators (e.g., "g g circle" or "g > circle rect") are handled through
     * recursion - the recursive call to select() below will parse remaining combinators.
     *
     * Uses LinkedHashSet to eliminate duplicates that can occur when nested containers
     * both match the parent selector (e.g., "g circle" with nested groups).
     */
    private static List<SvgElement> selectWithDescendantCombinator(ElementContainer container, String selector) {
        // Split on first whitespace only (limit 2) - remaining combinators handled recursively
        String[] parts = selector.split(/\s+/, 2)
        String parentSelector = parts[0]
        String childSelector = parts[1]

        // Special case: "svg ..." when container is the svg element itself
        // In this case, treat "svg" as matching the current container and continue with childSelector
        if (parentSelector == 'svg') {
            return select(container, childSelector)
        }

        // Use LinkedHashSet to preserve order while eliminating duplicates
        Set<SvgElement> resultsSet = new LinkedHashSet<>()

        // Find all elements matching parent selector
        List<SvgElement> parents = selectSimple(container, parentSelector, true)

        // For each parent, find descendants matching child selector
        // Recursive call to select() handles any remaining combinators in childSelector
        parents.each { parent ->
            if (parent instanceof ElementContainer) {
                resultsSet.addAll(select(parent as ElementContainer, childSelector))
            }
        }

        return new ArrayList<>(resultsSet)
    }

    /**
     * Process a child selector that may contain combinators against a list of direct children.
     * This method handles the logic of matching direct children and recursively processing
     * remaining selectors.
     *
     * @param directChildren the list of direct children to match against
     * @param childSelector the selector to apply to direct children (may contain combinators)
     * @return list of matching elements
     */
    private static List<SvgElement> selectFromDirectChildren(List<SvgElement> directChildren, String childSelector) {
        List<SvgElement> results = []

        // Check for child combinator (>) first, as "g > circle" contains both > and space
        if (childSelector.contains('>') && hasChildCombinatorOutsideBrackets(childSelector)) {
            // Child combinator in childSelector: "g > circle"
            String[] childParts = childSelector.split(/\s*>\s*/, 2)
            String directChildType = childParts[0].trim()
            String remainingSelector = childParts[1].trim()

            // Find direct children matching the type, then find their direct children
            directChildren.each { child ->
                if (matchesSelector(child, directChildType) && child instanceof ElementContainer) {
                    List<SvgElement> grandchildren = (child as ElementContainer).children as List<SvgElement>

                    // If remainingSelector has combinators, evaluate recursively
                    if (hasCombinator(remainingSelector)) {
                        grandchildren.each { grandchild ->
                            if (grandchild instanceof ElementContainer) {
                                results.addAll(select(grandchild as ElementContainer, remainingSelector))
                            }
                        }
                    } else {
                        // Simple selector - filter grandchildren
                        results.addAll(grandchildren.findAll { matchesSelector(it, remainingSelector) })
                    }
                }
            }
        } else if (childSelector.contains(' ') && !isWithinBrackets(childSelector)) {
            // Space combinator (descendant) in childSelector: "g circle"
            String[] childParts = childSelector.split(/\s+/, 2)
            String directChildType = childParts[0]
            String remainingSelector = childParts[1]

            // Find direct children matching the type, then search within them
            directChildren.each { child ->
                if (matchesSelector(child, directChildType) && child instanceof ElementContainer) {
                    results.addAll(select(child as ElementContainer, remainingSelector))
                }
            }
        } else {
            // Simple selector - filter direct children
            results.addAll(directChildren.findAll { matchesSelector(it, childSelector) })
        }

        return results
    }

    /**
     * Select elements with child combinator (e.g., "g > circle").
     *
     * Note: Like descendant combinators, chained selectors (e.g., "svg g > circle")
     * are handled through recursion - the call to select() for parentSelector
     * will parse any combinators in that part of the selector.
     */
    private static List<SvgElement> selectWithChildCombinator(ElementContainer container, String selector) {
        // Split on first '>' only (limit 2) - remaining combinators handled recursively
        String[] parts = selector.split(/\s*>\s*/, 2)
        String parentSelector = parts[0].trim()
        String childSelector = parts[1].trim()

        // Special case: "svg > ..." where container IS svg
        if (parentSelector == 'svg' || parentSelector == '*') {
            List<SvgElement> directChildren = container.children as List<SvgElement>
            return selectFromDirectChildren(directChildren, childSelector)
        }

        // Find all elements matching parent selector (may contain combinators)
        // Use select() instead of selectSimple() to handle nested combinators
        List<SvgElement> parents = select(container, parentSelector)

        // For each parent, find direct children matching child selector
        List<SvgElement> results = []
        parents.each { parent ->
            if (parent instanceof ElementContainer) {
                List<SvgElement> directChildren = (parent as ElementContainer).children as List<SvgElement>
                results.addAll(selectFromDirectChildren(directChildren, childSelector))
            }
        }

        return results
    }

    /**
     * Select elements matching a simple selector (no combinators).
     *
     * @param container the container to search in
     * @param selector the simple CSS selector
     * @param recursive whether to search recursively (descendants) or just direct children
     * @return list of matching elements
     */
    private static List<SvgElement> selectSimple(ElementContainer container, String selector, boolean recursive) {
        List<SvgElement> candidates = recursive ? container.descendants() : container.children

        return candidates.findAll { element ->
            matchesSelector(element, selector)
        }
    }

    /**
     * Check if an element matches a simple CSS selector (may be compound).
     * Returns false if the selector is malformed.
     */
    private static boolean matchesSelector(SvgElement element, String selector) {
        selector = selector.trim()

        // Universal selector
        if (selector == '*') {
            return true
        }

        // Parse compound selector (e.g., "circle[fill='red']", "rect:first-child")
        String typeSelector = null
        List<String> modifiers = []
        boolean hasModifierSyntax = selector.contains('[') || selector.contains(':')

        // Extract type selector and modifiers
        if (selector.startsWith('#') || selector.startsWith('.') || selector.startsWith('[') || selector.startsWith(':')) {
            // No type selector, just modifiers
            modifiers = parseModifiers(selector)
        } else {
            // Has type selector
            int firstModifier = findFirstModifierIndex(selector)
            if (firstModifier >= 0) {
                typeSelector = selector.substring(0, firstModifier)
                modifiers = parseModifiers(selector.substring(firstModifier))
            } else {
                typeSelector = selector
            }
        }

        // If selector had modifier syntax but parsing returned empty list, it's malformed
        if (hasModifierSyntax && modifiers.isEmpty()) {
            return false
        }

        // Check type selector first
        if (typeSelector != null && !typeSelector.isEmpty()) {
            if (element.getName() != typeSelector) {
                return false
            }
        }

        // Check all modifiers
        for (String modifier : modifiers) {
            if (!matchesModifier(element, modifier)) {
                return false
            }
        }

        return true
    }

    /**
     * Find index of first modifier in selector.
     */
    private static int findFirstModifierIndex(String selector) {
        int minIndex = Integer.MAX_VALUE
        String[] modifierStarts = ['#', '.', '[', ':']
        for (String start : modifierStarts) {
            int index = selector.indexOf(start)
            if (index >= 0 && index < minIndex) {
                minIndex = index
            }
        }
        return minIndex == Integer.MAX_VALUE ? -1 : minIndex
    }

    /**
     * Parse modifiers from selector (ID, class, attribute, pseudo-class).
     * Returns empty list if selector is malformed (unclosed brackets/parentheses).
     */
    private static List<String> parseModifiers(String modifierString) {
        List<String> modifiers = []
        int i = 0
        while (i < modifierString.length()) {
            char c = modifierString.charAt(i)

            if (c == '#' || c == '.') {
                // ID or class selector - read until next modifier
                int start = i
                i++
                while (i < modifierString.length()) {
                    char next = modifierString.charAt(i)
                    if (next == '#' || next == '.' || next == '[' || next == ':') {
                        break
                    }
                    i++
                }
                modifiers.add(modifierString.substring(start, i))
            } else if (c == '[') {
                // Attribute selector - read until ]
                int start = i
                int closeBracket = modifierString.indexOf(']', i)
                if (closeBracket == -1) {
                    // Malformed selector - unclosed bracket
                    return []
                }
                i = closeBracket + 1
                modifiers.add(modifierString.substring(start, i))
            } else if (c == ':') {
                // Pseudo-class - read until next modifier or end
                int start = i
                i++
                // Handle :nth-child(n) with parentheses
                if (modifierString.substring(i).startsWith('nth-child(')) {
                    int closeParen = modifierString.indexOf(')', i)
                    if (closeParen == -1) {
                        // Malformed selector - unclosed parenthesis
                        return []
                    }
                    i = closeParen + 1
                } else {
                    while (i < modifierString.length()) {
                        char next = modifierString.charAt(i)
                        if (next == '#' || next == '.' || next == '[' || next == ':') {
                            break
                        }
                        i++
                    }
                }
                modifiers.add(modifierString.substring(start, i))
            } else {
                i++
            }
        }
        return modifiers
    }

    /**
     * Check if element matches a single modifier.
     */
    private static boolean matchesModifier(SvgElement element, String modifier) {
        if (modifier.startsWith('#')) {
            // ID selector
            String id = modifier.substring(1)
            return element.getId() == id
        } else if (modifier.startsWith('.')) {
            // Class selector
            String className = modifier.substring(1)
            return element.hasClass(className)
        } else if (modifier.startsWith('[')) {
            // Attribute selector
            return matchesAttributeSelector(element, modifier)
        } else if (modifier.startsWith(':')) {
            // Pseudo-class
            return matchesPseudoClass(element, modifier)
        }
        return false
    }

    /**
     * Match attribute selectors: [fill="red"], [stroke]
     *
     * Supports standard CSS attribute selector syntax:
     * - [attribute] - has attribute
     * - [attribute="value"] - exact match
     * - [attribute=value] - exact match without quotes
     *
     * Note: Comparison operators ([width>100], [height<50]) are NOT supported as they
     * are not part of the CSS specification. For numeric comparisons, use filter() instead:
     * <code>svg.filter { it.getAttribute('width') as int > 100 }</code>
     */
    private static boolean matchesAttributeSelector(SvgElement element, String selector) {
        String inner = selector.substring(1, selector.length() - 1).trim()

        // [attribute] - has attribute (check for '=' to distinguish from [attr=value])
        if (!inner.contains('=')) {
            return element.getAttribute(inner) != null
        }

        // Try matching with quotes first (handles whitespace around '=')
        // Pattern: attribute name, optional spaces, =, optional spaces, quote, value, quote
        def quotedMatcher = inner =~ /^([^=]+?)\s*=\s*["']([^"']*)["']$/
        if (quotedMatcher.matches()) {
            String attrName = quotedMatcher.group(1).trim()
            String attrValue = quotedMatcher.group(2)
            return element.getAttribute(attrName) == attrValue
        }

        // [attribute=value] - exact match without quotes
        if (inner.contains('=')) {
            String[] parts = inner.split('=', 2)
            String attrName = parts[0].trim()
            String attrValue = parts[1].trim()
            return element.getAttribute(attrName) == attrValue
        }

        return false
    }

    /**
     * Match pseudo-class selectors: :first-child, :last-child, :nth-child(n)
     */
    private static boolean matchesPseudoClass(SvgElement element, String pseudoClass) {
        // Remove leading colon
        pseudoClass = pseudoClass.substring(1)

        // Get parent and siblings
        if (!(element.parent instanceof ElementContainer)) {
            return false
        }

        ElementContainer parent = element.parent as ElementContainer
        List<SvgElement> siblings = parent.children

        // :first-child
        if (pseudoClass == 'first-child') {
            return siblings.indexOf(element) == 0
        }

        // :last-child
        if (pseudoClass == 'last-child') {
            return siblings.indexOf(element) == siblings.size() - 1
        }

        // :nth-child(n)
        if (pseudoClass.startsWith('nth-child(') && pseudoClass.endsWith(')')) {
            String nValue = pseudoClass.substring(10, pseudoClass.length() - 1)
            try {
                int n = Integer.parseInt(nValue)
                int index = siblings.indexOf(element)
                return index == n - 1 // CSS nth-child is 1-indexed
            } catch (NumberFormatException ignored) {
                return false
            }
        }

        return false
    }
}
