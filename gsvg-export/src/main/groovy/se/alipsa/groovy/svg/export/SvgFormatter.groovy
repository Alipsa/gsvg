package se.alipsa.groovy.svg.export

import se.alipsa.groovy.svg.ElementContainer
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.SvgElement
import org.dom4j.Attribute
import org.dom4j.Namespace

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

        formatElement(svg, sb, 0, indent, newline, sortAttributes, groupElements, [:])

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
    @SuppressWarnings('UnusedMethodParameter')
    static Svg format(Svg svg, Map options = [:]) {
        // Formatting is applied during toString(), so we just return the same object
        // Users should call prettify() to get the formatted string
        return svg
    }

    /**
     * Formats a single element recursively.
     */
    private static void formatElement(SvgElement element, StringBuilder sb, int depth,
                                      String indent, String newline, boolean sortAttrs, boolean groupElems,
                                      Map<String, String> inheritedNamespaces) {
        // Add indentation
        sb.append(indent * depth)

        // Start tag
        sb.append('<')
        sb.append(element.element.qualifiedName)

        Map<String, String> namespaces = new LinkedHashMap<>(inheritedNamespaces)
        Map<String, String> declarations = namespaceDeclarations(element, namespaces)
        declarations.each { prefix, uri ->
            sb.append(prefix ? " xmlns:${prefix}=\"" : ' xmlns="')
            sb.append(escapeXml(uri))
            sb.append('"')
            namespaces[prefix] = uri
        }

        // Add attributes
        def attributes = element.element.attributes().collectEntries { Attribute attr ->
            [(attr.qualifiedName): attr.value]
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

        boolean hasChildren = element instanceof ElementContainer &&
                !(element as ElementContainer).children.isEmpty()
        String textContent = element.element.getText()
        boolean hasTextContent = textContent != null && !textContent.isEmpty()

        if (!hasChildren && !hasTextContent) {
            // Self-closing tag
            sb.append('/>')
            sb.append(newline)
        } else {
            sb.append('>')

            def container = element as ElementContainer
            def children = container.children

            // Keep text-only elements (for example title, text, and style) on one line.
            boolean simpleContent = children.isEmpty() && hasTextContent

            if (simpleContent) {
                // Keep text content on same line
                sb.append(escapeXml(textContent))
                sb.append('</')
                sb.append(element.element.qualifiedName)
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
                        formatElement(child as SvgElement, sb, depth + 1, indent, newline, sortAttrs, groupElems, namespaces)
                        lastType = currentType
                    }
                } else {
                    children.each { child ->
                        formatElement(child as SvgElement, sb, depth + 1, indent, newline, sortAttrs, groupElems, namespaces)
                    }
                }

                // Closing tag
                sb.append(indent * depth)
                sb.append('</')
                sb.append(element.element.qualifiedName)
                sb.append('>')
                sb.append(newline)
            }
        }
    }

    /** Returns namespace declarations needed for the current element. */
    private static Map<String, String> namespaceDeclarations(SvgElement element, Map<String, String> inheritedNamespaces) {
        Map<String, String> declarations = new LinkedHashMap<>()
        addNamespaceDeclaration(declarations, inheritedNamespaces, element.element.namespace)
        element.element.declaredNamespaces().each { Namespace namespace ->
            addNamespaceDeclaration(declarations, inheritedNamespaces, namespace)
        }
        element.element.attributes().each { Attribute attribute ->
            Namespace namespace = attribute.getQName().namespace
            if (namespace != Namespace.NO_NAMESPACE && namespace.prefix != 'xml') {
                addNamespaceDeclaration(declarations, inheritedNamespaces, namespace)
            }
        }
        declarations
    }

    private static void addNamespaceDeclaration(Map<String, String> declarations,
                                                Map<String, String> inheritedNamespaces,
                                                Namespace namespace) {
        if (namespace != null && namespace.URI && inheritedNamespaces[namespace.prefix] != namespace.URI) {
            declarations[namespace.prefix] = namespace.URI
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
