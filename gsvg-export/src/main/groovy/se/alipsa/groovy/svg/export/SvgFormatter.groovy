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

        appendDocumentComments(svg, sb, newline, true)
        formatElement(svg, sb, 0, indent, newline, sortAttributes, groupElements, [:])
        appendDocumentComments(svg, sb, newline, false)

        return sb.toString()
    }

    /** Appends document-level comments before or after the root element. */
    private static void appendDocumentComments(Svg svg, StringBuilder sb, String newline, boolean beforeRoot) {
        int rootIndex = svg.document.content().indexOf(svg.element)
        svg.document.content().eachWithIndex { Object node, int index ->
            if (node instanceof org.dom4j.Comment && (beforeRoot ? index < rootIndex : index > rootIndex)) {
                sb.append('<!--')
                sb.append((node as org.dom4j.Comment).text)
                sb.append('-->')
                sb.append(newline)
            }
        }
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
        Map<String, String> declarations = namespaceDeclarations(element.element, namespaces)
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
        boolean hasComment = element.element.content().any { node -> node instanceof org.dom4j.Comment }
        boolean hasCdata = element.element.content().any { node -> node instanceof org.dom4j.CDATA }
        boolean hasMixedText = hasTextContent || hasCdata

        if (!hasChildren && !hasMixedText && !hasComment) {
            // Self-closing tag
            sb.append('/>')
            sb.append(newline)
        } else {
            sb.append('>')

            def container = element as ElementContainer
            def children = container.children

            // Keep text-only elements (for example title, text, and style) on one line.
            boolean simpleContent = children.isEmpty() && hasTextContent && !hasComment && !hasCdata

            if (simpleContent) {
                // Keep text content on same line
                sb.append(escapeXml(textContent))
                sb.append('</')
                sb.append(element.element.qualifiedName)
                sb.append('>')
                sb.append(newline)
            } else if (hasMixedText) {
                appendMixedContent(element.element, sb, sortAttrs, namespaces)
                sb.append('</')
                sb.append(element.element.qualifiedName)
                sb.append('>')
                sb.append(newline)
            } else {
                // Format children with indentation
                sb.append(newline)

                if (hasComment) {
                    appendIndentedContent(element.element, children, sb, depth, indent, newline, sortAttrs, groupElems, namespaces)
                } else if (groupElems) {
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

    /** Writes element children and comments on separate, correctly indented lines. */
    private static void appendIndentedContent(org.dom4j.Element element, List<SvgElement> children, StringBuilder sb,
                                              int depth, String indent, String newline, boolean sortAttrs,
                                              boolean groupElems, Map<String, String> namespaces) {
        Map<org.dom4j.Element, SvgElement> wrappers = children.collectEntries { SvgElement child -> [(child.element): child] }
        element.content().each { node ->
            if (node instanceof org.dom4j.Comment) {
                sb.append(indent * (depth + 1)).append('<!--').append((node as org.dom4j.Comment).text).append('-->').append(newline)
            } else if (node instanceof org.dom4j.Element) {
                SvgElement child = wrappers[node as org.dom4j.Element]
                if (child != null) {
                    formatElement(child, sb, depth + 1, indent, newline, sortAttrs, groupElems, namespaces)
                }
            }
        }
    }

    /** Writes mixed DOM content in order without losing CDATA, comments, or namespaces. */
    private static void appendMixedContent(org.dom4j.Element element, StringBuilder sb,
                                           boolean sortAttrs, Map<String, String> inheritedNamespaces) {
        element.content().each { node ->
            if (node instanceof org.dom4j.Text) {
                sb.append(escapeXml((node as org.dom4j.Text).text))
            } else if (node instanceof org.dom4j.CDATA) {
                sb.append('<![CDATA[').append((node as org.dom4j.CDATA).text).append(']]>')
            } else if (node instanceof org.dom4j.Comment) {
                sb.append('<!--').append((node as org.dom4j.Comment).text).append('-->')
            } else if (node instanceof org.dom4j.Element) {
                appendInlineElement(node as org.dom4j.Element, sb, sortAttrs, inheritedNamespaces)
            }
        }
    }

    /** Serializes a DOM element inline while keeping namespace declarations scoped to its parent. */
    private static void appendInlineElement(org.dom4j.Element element, StringBuilder sb,
                                            boolean sortAttrs, Map<String, String> inheritedNamespaces) {
        Map<String, String> namespaces = new LinkedHashMap<>(inheritedNamespaces)
        sb.append('<').append(element.qualifiedName)
        namespaceDeclarations(element, namespaces).each { prefix, uri ->
            sb.append(prefix ? " xmlns:${prefix}=\"" : ' xmlns="').append(escapeXml(uri)).append('"')
            namespaces[prefix] = uri
        }
        List<Attribute> attributes = element.attributes() as List<Attribute>
        if (sortAttrs) {
            attributes = attributes.sort { Attribute attribute -> attribute.qualifiedName }
        }
        attributes.each { Attribute attribute ->
            sb.append(' ').append(attribute.qualifiedName).append('="').append(escapeXml(attribute.value)).append('"')
        }
        if (element.content().isEmpty()) {
            sb.append('/>')
        } else {
            sb.append('>')
            appendMixedContent(element, sb, sortAttrs, namespaces)
            sb.append('</').append(element.qualifiedName).append('>')
        }
    }

    /** Returns namespace declarations needed for the current element. */
    private static Map<String, String> namespaceDeclarations(org.dom4j.Element element, Map<String, String> inheritedNamespaces) {
        Map<String, String> declarations = new LinkedHashMap<>()
        addNamespaceDeclaration(declarations, inheritedNamespaces, element.namespace)
        element.declaredNamespaces().each { Namespace namespace ->
            addNamespaceDeclaration(declarations, inheritedNamespaces, namespace)
        }
        element.attributes().each { Attribute attribute ->
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
