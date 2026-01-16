package se.alipsa.groovy.svg.export

import se.alipsa.groovy.svg.*
import se.alipsa.groovy.svg.io.SvgReader

/**
 * Provides SVG prettification capabilities for human-readable output.
 *
 * @since 1.0.0
 */
class SvgFormatter {

    /**
     * Prettifies an SVG and returns formatted XML string.
     *
     * @param svg The SVG to format
     * @param options Formatting options:
     *   - indent: Indentation string (default: "  ")
     *   - newline: Newline character (default: "\n")
     *   - sortAttributes: Sort attributes alphabetically (default: false)
     *   - groupElements: Group similar elements with blank lines (default: false)
     *   - maxLineLength: Max line length for wrapping (default: null, no wrapping)
     * @return Formatted XML string
     */
    static String prettify(Svg svg, Map options = [:]) {
        String indent = options.indent ?: '  '
        String newline = options.newline ?: '\n'
        boolean sortAttributes = options.sortAttributes ?: false
        boolean groupElements = options.groupElements ?: false

        StringBuilder sb = new StringBuilder()
        sb.append('<?xml version="1.0" encoding="UTF-8"?>')
        sb.append(newline)

        formatElement(svg, sb, 0, indent, newline, sortAttributes, groupElements)

        return sb.toString()
    }

    /**
     * Formats an SVG and returns a new Svg object with formatted XML.
     * Note: This returns the same Svg object, as formatting is applied during serialization.
     *
     * @param svg The SVG to format
     * @param options Formatting options (see prettify method)
     * @return The SVG object (same instance)
     */
    static Svg format(Svg svg, Map options = [:]) {
        // Formatting is applied during toString(), so we just return the same object
        // Users should call prettify() to get the formatted string
        return svg
    }

    /**
     * Formats a single element recursively.
     */
    private static void formatElement(SvgElement element, StringBuilder sb, int depth,
                                      String indent, String newline, boolean sortAttrs, boolean groupElems) {
        // Add indentation
        sb.append(indent * depth)

        // Start tag
        sb.append('<')
        sb.append(element.getName())

        // Add attributes
        def attributes = element.element.attributes().collectEntries { attr ->
            [(attr.name): attr.value]
        }
        if (sortAttrs) {
            attributes = attributes.sort()
        }

        attributes.each { name, value ->
            sb.append(' ')
            sb.append(name)
            sb.append('="')
            sb.append(escapeXml(value))
            sb.append('"')
        }

        // Check if element has children
        boolean hasChildren = element instanceof ElementContainer &&
                !(element as ElementContainer).children.isEmpty()

        if (!hasChildren) {
            // Self-closing tag
            sb.append('/>')
            sb.append(newline)
        } else {
            sb.append('>')

            def container = element as ElementContainer
            def children = container.children

            // Check if element has text content but no child elements
            // This applies to elements like <title>Text</title>, <text>Content</text>, etc.
            boolean hasTextContent = element.element.getText() != null && !element.element.getText().isEmpty()
            boolean simpleContent = children.isEmpty() && hasTextContent

            if (simpleContent) {
                // Keep text content on same line
                sb.append(escapeXml(element.element.getText()))
                sb.append('</')
                sb.append(element.getName())
                sb.append('>')
                sb.append(newline)
            } else {
                // Format children with indentation
                sb.append(newline)

                if (groupElems) {
                    // Group similar elements
                    String lastType = null
                    children.each { child ->
                        String currentType = (child as SvgElement).getName()
                        if (lastType != null && lastType != currentType) {
                            sb.append(newline)
                        }
                        formatElement(child as SvgElement, sb, depth + 1, indent, newline, sortAttrs, groupElems)
                        lastType = currentType
                    }
                } else {
                    children.each { child ->
                        formatElement(child as SvgElement, sb, depth + 1, indent, newline, sortAttrs, groupElems)
                    }
                }

                // Closing tag
                sb.append(indent * depth)
                sb.append('</')
                sb.append(element.getName())
                sb.append('>')
                sb.append(newline)
            }
        }
    }

    /**
     * Escapes XML special characters.
     */
    private static String escapeXml(String text) {
        if (!text) {
            return text
        }

        return text.replace('&', '&amp;')
                .replace('<', '&lt;')
                .replace('>', '&gt;')
                .replace('"', '&quot;')
                .replace("'", '&apos;')
    }

    /**
     * Pretty prints an SVG with default settings.
     *
     * @param svg The SVG to format
     * @return Formatted XML string with 2-space indentation
     */
    static String prettyPrint(Svg svg) {
        return prettify(svg, [:])
    }

    /**
     * Pretty prints an SVG with custom indentation.
     *
     * @param svg The SVG to format
     * @param indentSize Number of spaces for indentation
     * @return Formatted XML string
     */
    static String prettyPrint(Svg svg, int indentSize) {
        String indent = ' ' * indentSize
        return prettify(svg, [indent: indent])
    }

    /**
     * Formats SVG with sorted attributes for consistent output.
     *
     * @param svg The SVG to format
     * @return Formatted XML string with sorted attributes
     */
    static String prettifyWithSortedAttributes(Svg svg) {
        return prettify(svg, [sortAttributes: true])
    }

    /**
     * Formats SVG with element grouping for better readability.
     *
     * @param svg The SVG to format
     * @return Formatted XML string with grouped similar elements
     */
    static String prettifyWithGrouping(Svg svg) {
        return prettify(svg, [groupElements: true])
    }
}
