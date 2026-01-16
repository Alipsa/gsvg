package se.alipsa.groovy.svg.css

import groovy.transform.CompileStatic

/**
 * Represents a CSS stylesheet containing multiple CSS rules.
 * This class is mutable and supports fluent API for building stylesheets.
 *
 * <p>Example usage:
 * <pre>{@code
 * CssStyleSheet stylesheet = CssStyleSheet.empty()
 *     .addRule('.highlight', [fill: 'red', stroke: 'black'])
 *     .addRule('#logo', [transform: 'scale(2)'])
 *
 * String css = stylesheet.toString()
 * }</pre>
 */
@CompileStatic
class CssStyleSheet {

    private final List<CssRule> rules = []

    /**
     * Creates an empty CSS stylesheet.
     *
     * @return a new empty CssStyleSheet
     */
    static CssStyleSheet empty() {
        new CssStyleSheet()
    }

    /**
     * Parses a CSS text string into a CssStyleSheet.
     *
     * @param cssText the CSS text containing one or more rules
     * @return a new CssStyleSheet with parsed rules
     * @throws IllegalArgumentException if the CSS cannot be parsed
     */
    static CssStyleSheet parse(String cssText) {
        CssParser.parseStyleSheet(cssText)
    }

    /**
     * Adds a CSS rule to this stylesheet.
     *
     * @param selector the CSS selector
     * @param declarations the CSS property declarations
     * @return this stylesheet for chaining
     */
    CssStyleSheet addRule(String selector, Map<String, String> declarations) {
        rules.add(CssRule.of(selector, declarations))
        this
    }

    /**
     * Adds a CSS rule to this stylesheet.
     *
     * @param rule the CSS rule to add
     * @return this stylesheet for chaining
     */
    CssStyleSheet addRule(CssRule rule) {
        if (rule != null) {
            rules.add(rule)
        }
        this
    }

    /**
     * Removes all rules with the specified selector.
     *
     * @param selector the CSS selector
     * @return this stylesheet for chaining
     */
    CssStyleSheet removeRule(String selector) {
        rules.removeAll { it.selector == selector }
        this
    }

    /**
     * Returns all CSS rules in this stylesheet.
     *
     * @return an unmodifiable list of CSS rules
     */
    List<CssRule> getRules() {
        Collections.unmodifiableList(rules)
    }

    /**
     * Finds all rules matching the specified selector.
     *
     * @param selector the CSS selector to search for
     * @return a list of matching rules
     */
    List<CssRule> findBySelector(String selector) {
        rules.findAll { it.selector == selector }
    }

    /**
     * Finds the first rule matching the specified selector.
     *
     * @param selector the CSS selector to search for
     * @return the first matching rule, or null if not found
     */
    CssRule findFirstBySelector(String selector) {
        rules.find { it.selector == selector }
    }

    /**
     * Checks if this stylesheet is empty (contains no rules).
     *
     * @return true if empty, false otherwise
     */
    boolean isEmpty() {
        rules.isEmpty()
    }

    /**
     * Returns the number of rules in this stylesheet.
     *
     * @return the rule count
     */
    int size() {
        rules.size()
    }

    /**
     * Removes all rules from this stylesheet.
     *
     * @return this stylesheet for chaining
     */
    CssStyleSheet clear() {
        rules.clear()
        this
    }

    /**
     * Returns the stylesheet as a CSS text string.
     *
     * @return the CSS text with all rules
     */
    @Override
    String toString() {
        if (rules.isEmpty()) {
            return ''
        }
        rules.collect { it.toString() }.join('\n')
    }
}
