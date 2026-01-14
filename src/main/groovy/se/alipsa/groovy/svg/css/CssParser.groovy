package se.alipsa.groovy.svg.css

import com.helger.css.decl.CSSDeclaration
import com.helger.css.decl.CSSStyleRule
import com.helger.css.decl.CascadingStyleSheet
import com.helger.css.reader.CSSReader
import com.helger.css.writer.CSSWriter
import com.helger.css.writer.CSSWriterSettings
import groovy.transform.CompileStatic

/**
 * Utility for parsing and formatting CSS using the ph-css library.
 * Provides static methods for converting between CSS text and object representations.
 */
@CompileStatic
class CssParser {

    private static final CSSWriter CSS_WRITER = new CSSWriter(new CSSWriterSettings(false))

    /**
     * Parses a CSS stylesheet from text.
     *
     * @param cssText the CSS text to parse
     * @return a CssStyleSheet with all parsed rules
     * @throws IllegalArgumentException if parsing fails
     */
    static CssStyleSheet parseStyleSheet(String cssText) {
        if (!cssText || cssText.trim().isEmpty()) {
            return CssStyleSheet.empty()
        }

        CascadingStyleSheet css = CSSReader.readFromString(cssText)
        if (css == null) {
            throw new IllegalArgumentException("Failed to parse CSS: ${cssText}")
        }

        CssStyleSheet stylesheet = CssStyleSheet.empty()
        css.getAllStyleRules().each { CSSStyleRule rule ->
            // Get all selectors for this rule (can be multiple)
            rule.getAllSelectors().each { selector ->
                String selectorText = selector.getAsCSSString()

                // Extract declarations
                Map<String, String> declarations = new LinkedHashMap<>()
                rule.getAllDeclarations().each { CSSDeclaration decl ->
                    declarations[decl.getProperty()] = decl.getExpressionAsCSSString()
                }

                stylesheet.addRule(selectorText, declarations)
            }
        }

        return stylesheet
    }

    /**
     * Parses a single CSS rule from text.
     *
     * @param ruleText the CSS rule text (e.g., ".class { fill: red; }")
     * @return a CssRule
     * @throws IllegalArgumentException if parsing fails
     */
    static CssRule parseRule(String ruleText) {
        // Wrap the rule in a minimal stylesheet to parse it
        String wrappedCss = ruleText.trim()

        CascadingStyleSheet css = CSSReader.readFromString(wrappedCss)
        if (css == null || css.getStyleRuleCount() == 0) {
            throw new IllegalArgumentException("Failed to parse CSS rule: ${ruleText}")
        }

        CSSStyleRule rule = css.getAllStyleRules().get(0)
        String selector = rule.getAllSelectors().get(0).getAsCSSString()

        Map<String, String> declarations = new LinkedHashMap<>()
        rule.getAllDeclarations().each { CSSDeclaration decl ->
            declarations[decl.getProperty()] = decl.getExpressionAsCSSString()
        }

        return CssRule.of(selector, declarations)
    }

    /**
     * Parses an inline style attribute value (e.g., "fill: red; stroke: blue;").
     *
     * @param styleAttr the style attribute value
     * @return a map of property names to values
     */
    static Map<String, String> parseInlineStyle(String styleAttr) {
        if (!styleAttr || styleAttr.trim().isEmpty()) {
            return [:]
        }

        Map<String, String> result = new LinkedHashMap<>()

        // Parse as a CSS declaration block
        // Wrap in a dummy selector to make it parseable
        String wrappedCss = "dummy { ${styleAttr} }"

        try {
            CascadingStyleSheet css = CSSReader.readFromString(wrappedCss)
            if (css != null && css.getStyleRuleCount() > 0) {
                CSSStyleRule rule = css.getAllStyleRules().get(0)
                rule.getAllDeclarations().each { CSSDeclaration decl ->
                    result[decl.getProperty()] = decl.getExpressionAsCSSString()
                }
            }
        } catch (Exception e) {
            // If parsing fails, try simple split approach
            return parseInlineStyleSimple(styleAttr)
        }

        return result
    }

    /**
     * Simple fallback parser for inline styles using string split.
     */
    private static Map<String, String> parseInlineStyleSimple(String styleAttr) {
        Map<String, String> result = new LinkedHashMap<>()

        styleAttr.split(';').each { String declaration ->
            String[] parts = declaration.split(':', 2)
            if (parts.length == 2) {
                String property = parts[0].trim()
                String value = parts[1].trim()
                if (property && value) {
                    result[property] = value
                }
            }
        }

        return result
    }

    /**
     * Converts a map of style properties to an inline style string.
     *
     * @param styleMap the style properties
     * @return the inline style string (e.g., "fill: red; stroke: blue;")
     */
    static String toInlineStyle(Map<String, String> styleMap) {
        if (!styleMap || styleMap.isEmpty()) {
            return ''
        }

        styleMap.collect { key, value ->
            "${key}: ${value}"
        }.join('; ')
    }
}
