package se.alipsa.groovy.svg.css

import groovy.transform.CompileStatic
import groovy.transform.Immutable

/**
 * Represents a single CSS rule with a selector and declarations.
 * This class is immutable - all modification methods return a new instance.
 *
 * <p>Example usage:
 * <pre>{@code
 * CssRule rule = CssRule.of('.highlight', [fill: 'red', stroke: 'black'])
 * CssRule modified = rule.withDeclaration('opacity', '0.8')
 * String css = rule.toString()  // ".highlight { fill: red; stroke: black; }"
 * }</pre>
 */
@CompileStatic
@Immutable(copyWith = true)
class CssRule {

    /**
     * The CSS selector (e.g., ".highlight", "#logo", "rect")
     */
    String selector

    /**
     * The CSS declarations as a map of property names to values
     */
    Map<String, String> declarations

    /**
     * Creates a CSS rule with the specified selector and declarations.
     *
     * @param selector the CSS selector
     * @param declarations the CSS property declarations
     * @return a new CssRule instance
     */
    static CssRule of(String selector, Map<String, String> declarations) {
        new CssRule(selector, new LinkedHashMap<>(declarations))
    }

    /**
     * Parses a CSS rule string into a CssRule object.
     *
     * @param cssRuleText the CSS rule text (e.g., ".class { fill: red; }")
     * @return a new CssRule instance
     * @throws IllegalArgumentException if the CSS rule cannot be parsed
     */
    static CssRule parse(String cssRuleText) {
        CssParser.parseRule(cssRuleText)
    }

    /**
     * Gets the value of a specific CSS property.
     *
     * @param property the property name
     * @return the property value, or null if not present
     */
    String getDeclaration(String property) {
        declarations?.get(property)
    }

    /**
     * Returns a new CssRule with an additional or updated declaration.
     *
     * @param property the property name
     * @param value the property value
     * @return a new CssRule with the updated declaration
     */
    CssRule withDeclaration(String property, String value) {
        Map<String, String> newDeclarations = new LinkedHashMap<>(declarations ?: [:])
        newDeclarations[property] = value
        new CssRule(selector, newDeclarations)
    }

    /**
     * Returns a new CssRule without the specified declaration.
     *
     * @param property the property name to remove
     * @return a new CssRule without the specified declaration
     */
    CssRule withoutDeclaration(String property) {
        if (!declarations || !declarations.containsKey(property)) {
            return this
        }
        Map<String, String> newDeclarations = new LinkedHashMap<>(declarations)
        newDeclarations.remove(property)
        new CssRule(selector, newDeclarations)
    }

    /**
     * Returns the CSS rule as a formatted string.
     *
     * @return the CSS rule text (e.g., ".class { fill: red; stroke: black; }")
     */
    @Override
    String toString() {
        if (!declarations || declarations.isEmpty()) {
            return "${selector} { }"
        }

        String declarationsText = declarations.collect { key, value ->
            "${key}: ${value}"
        }.join('; ')

        return "${selector} { ${declarationsText}; }"
    }
}
