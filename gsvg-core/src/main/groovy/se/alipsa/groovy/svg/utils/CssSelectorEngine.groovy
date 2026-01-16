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
        if (selector.contains('>') && !selector.startsWith('[')) {
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
     * Check if spaces are within attribute selector brackets.
     */
    private static boolean isWithinBrackets(String selector) {
        int bracketStart = selector.indexOf('[')
        int bracketEnd = selector.indexOf(']')
        if (bracketStart >= 0 && bracketEnd > bracketStart) {
            String afterBracket = selector.substring(bracketEnd + 1)
            return !afterBracket.contains(' ')
        }
        return false
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
     */
    private static List<SvgElement> selectWithDescendantCombinator(ElementContainer container, String selector) {
        String[] parts = selector.split(/\s+/, 2)
        String parentSelector = parts[0]
        String childSelector = parts[1]

        List<SvgElement> results = []

        // Find all elements matching parent selector
        List<SvgElement> parents = selectSimple(container, parentSelector, true)

        // For each parent, find descendants matching child selector
        parents.each { parent ->
            if (parent instanceof ElementContainer) {
                results.addAll(select(parent as ElementContainer, childSelector))
            }
        }

        return results
    }

    /**
     * Select elements with child combinator (e.g., "g > circle").
     */
    private static List<SvgElement> selectWithChildCombinator(ElementContainer container, String selector) {
        String[] parts = selector.split(/\s*>\s*/, 2)
        String parentSelector = parts[0].trim()
        String childSelector = parts[1].trim()

        List<SvgElement> results = []

        // Special case: "svg > circle" where container IS svg
        if (parentSelector == 'svg' || parentSelector == '*') {
            // Select direct children matching child selector
            return selectSimple(container, childSelector, false)
        }

        // Find all elements matching parent selector
        List<SvgElement> parents = selectSimple(container, parentSelector, true)

        // For each parent, find direct children matching child selector
        parents.each { parent ->
            if (parent instanceof ElementContainer) {
                results.addAll(selectSimple(parent as ElementContainer, childSelector, false))
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
                i = modifierString.indexOf(']', i) + 1
                modifiers.add(modifierString.substring(start, i))
            } else if (c == ':') {
                // Pseudo-class - read until next modifier or end
                int start = i
                i++
                // Handle :nth-child(n) with parentheses
                if (modifierString.substring(i).startsWith('nth-child(')) {
                    i = modifierString.indexOf(')', i) + 1
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
     * Match attribute selectors: [fill="red"], [stroke], [width>100]
     */
    private static boolean matchesAttributeSelector(SvgElement element, String selector) {
        String inner = selector.substring(1, selector.length() - 1)

        // [attribute] - has attribute
        if (!inner.contains('=') && !inner.contains('>') && !inner.contains('<')) {
            return element.getAttribute(inner) != null
        }

        // [attribute="value"] or [attribute='value'] - exact match
        if (inner.contains('="') || inner.contains("='")) {
            def matcher = inner =~ /^([^=]+)=["']([^"']*)["']$/
            if (matcher.matches()) {
                String attrName = matcher.group(1)
                String attrValue = matcher.group(2)
                return element.getAttribute(attrName) == attrValue
            }
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
