package se.alipsa.groovy.svg.export

import se.alipsa.groovy.svg.*
import se.alipsa.groovy.svg.io.SvgReader
import se.alipsa.groovy.svg.utils.NumberFormatter

/**
 * Provides SVG optimization capabilities to reduce file size.
 *
 * @since 1.0.0
 */
class SvgOptimizer {

    /**
     * Optimizes an SVG and returns a new optimized copy.
     *
     * @param svg The SVG to optimize
     * @param options Optimization options:
     *   - removeComments: Remove XML comments (default: true)
     *   - removeMetadata: Remove metadata elements (default: true)
     *   - removeUnusedDefs: Remove unused definitions (default: true)
     *   - collapseGroups: Collapse redundant groups (default: true)
     *   - precision: Number precision for rounding (default: 2)
     *   - minifyPathData: Minify path data (default: true)
     *   - removeDefaults: Remove default attribute values (default: true)
     *   - removeInvisible: Remove invisible elements (default: true)
     *   - shortenColors: Convert colors to shorter formats (default: true)
     * @return Optimized SVG
     */
    static Svg optimize(Svg svg, Map options = [:]) {
        // Clone the SVG to avoid modifying the original
        String svgString = svg.toXml()
        Svg optimized = SvgReader.parse(svgString)

        // Apply optimizations
        optimizeInPlace(optimized, options)

        return optimized
    }

    /**
     * Optimizes an SVG in place (modifies the original).
     *
     * @param svg The SVG to optimize
     * @param options Optimization options (see optimize method)
     */
    static void optimizeInPlace(Svg svg, Map options = [:]) {
        // Set defaults
        boolean removeMetadata = options.removeMetadata != null ? options.removeMetadata : true
        boolean removeUnusedDefs = options.removeUnusedDefs != null ? options.removeUnusedDefs : true
        boolean removeDefaults = options.removeDefaults != null ? options.removeDefaults : true
        boolean removeInvisible = options.removeInvisible != null ? options.removeInvisible : true
        boolean shortenColors = options.shortenColors != null ? options.shortenColors : true
        boolean minifyPathData = options.minifyPathData != null ? options.minifyPathData : true
        boolean collapseGroups = options.collapseGroups != null ? options.collapseGroups : true
        Integer precision = options.precision as Integer ?: 2

        // Remove metadata elements
        if (removeMetadata) {
            removeMetadataElements(svg)
        }

        // Remove invisible elements
        if (removeInvisible) {
            removeInvisibleElements(svg)
        }

        // Remove default attributes
        if (removeDefaults) {
            removeDefaultAttributes(svg)
        }

        // Shorten colors
        if (shortenColors) {
            shortenColorValues(svg)
        }

        // Round numbers to precision
        if (precision != null) {
            roundNumericAttributes(svg, precision)
        }

        // Minify path data
        if (minifyPathData) {
            minifyPaths(svg)
        }

        // Collapse redundant groups
        if (collapseGroups) {
            collapseRedundantGroups(svg)
        }

        // Remove unused defs (should be done last)
        if (removeUnusedDefs) {
            removeUnusedDefinitions(svg)
        }
    }

    /**
     * Removes metadata, title, and desc elements.
     */
    private static void removeMetadataElements(Svg svg) {
        def toRemove = []

        svg.children.each { element ->
            if (element instanceof Metadata || element instanceof Title || element instanceof Desc) {
                toRemove << element
            }
        }

        toRemove.each { svg.element.remove((it as SvgElement).element) }
    }

    /**
     * Removes elements with display:none or opacity:0.
     */
    private static void removeInvisibleElements(AbstractElementContainer container) {
        def toRemove = []

        container.children.each { element ->
            if (element instanceof SvgElement) {
                String display = element.getAttribute('display')
                String opacity = element.getAttribute('opacity')
                String visibility = element.getAttribute('visibility')

                if (display == 'none' || opacity == '0' || visibility == 'hidden') {
                    toRemove << element
                } else if (element instanceof AbstractElementContainer) {
                    removeInvisibleElements(element)
                }
            }
        }

        toRemove.each { container.element.remove((it as SvgElement).element) }
    }

    /**
     * Removes default attribute values.
     */
    private static void removeDefaultAttributes(AbstractElementContainer container) {
        def defaultValues = [
                'fill': 'black',
                'stroke': 'none',
                'stroke-width': '1',
                'opacity': '1',
                'fill-opacity': '1',
                'stroke-opacity': '1',
                'visibility': 'visible',
                'overflow': 'visible'
        ]

        container.children.each { element ->
            if (element instanceof SvgElement) {
                defaultValues.each { attr, defaultValue ->
                    if (element.getAttribute(attr) == defaultValue) {
                        element.removeAttribute(attr)
                    }
                }

                if (element instanceof AbstractElementContainer) {
                    removeDefaultAttributes(element)
                }
            }
        }
    }

    /**
     * Shortens color values (#RRGGBB -> #RGB where possible).
     */
    private static void shortenColorValues(AbstractElementContainer container) {
        def colorAttrs = ['fill', 'stroke', 'stop-color', 'flood-color', 'lighting-color']

        container.children.each { element ->
            if (element instanceof SvgElement) {
                colorAttrs.each { attr ->
                    String color = element.getAttribute(attr)
                    if (color && color.startsWith('#') && color.length() == 7) {
                        // Check if can be shortened
                        String r = color[1..2]
                        String g = color[3..4]
                        String b = color[5..6]

                        if (r[0] == r[1] && g[0] == g[1] && b[0] == b[1]) {
                            String shortened = "#${r[0]}${g[0]}${b[0]}"
                            element.addAttribute(attr, shortened)
                        }
                    }
                }

                if (element instanceof AbstractElementContainer) {
                    shortenColorValues(element)
                }
            }
        }
    }

    /**
     * Rounds numeric attributes to specified precision.
     */
    private static void roundNumericAttributes(AbstractElementContainer container, int precision) {
        def numericAttrs = ['x', 'y', 'width', 'height', 'cx', 'cy', 'r', 'rx', 'ry',
                            'x1', 'y1', 'x2', 'y2', 'offset', 'stroke-width', 'font-size']

        container.children.each { element ->
            if (element instanceof SvgElement) {
                numericAttrs.each { attr ->
                    String value = element.getAttribute(attr)
                    if (value) {
                        try {
                            BigDecimal num = new BigDecimal(value)
                            String rounded = NumberFormatter.format(num, precision)
                            element.addAttribute(attr, rounded)
                        } catch (NumberFormatException ignored) {
                            // Not a number, skip
                        }
                    }
                }

                if (element instanceof AbstractElementContainer) {
                    roundNumericAttributes(element, precision)
                }
            }
        }
    }

    /**
     * Minifies path data by removing unnecessary spaces.
     */
    private static void minifyPaths(AbstractElementContainer container) {
        container.children.each { element ->
            if (element instanceof Path) {
                String d = element.getAttribute('d')
                if (d) {
                    // Remove unnecessary spaces
                    d = d.replaceAll(/\s+/, ' ')
                            .replaceAll(/\s*,\s*/, ',')
                            .replaceAll(/([a-zA-Z])\s+/, '$1')
                            .replaceAll(/\s+([a-zA-Z])/, '$1')
                            .trim()
                    element.addAttribute('d', d)
                }
            } else if (element instanceof AbstractElementContainer) {
                minifyPaths(element)
            }
        }
    }

    /**
     * Collapses redundant groups (groups with only one child and no attributes).
     */
    private static void collapseRedundantGroups(AbstractElementContainer container) {
        def toCollapse = []

        container.children.each { element ->
            if (element instanceof G) {
                def children = element.children
                boolean hasAttributes = element.element.attributes().size() > 0

                if (children.size() == 1 && !hasAttributes) {
                    toCollapse << [parent: container, group: element, child: children[0]]
                } else {
                    collapseRedundantGroups(element)
                }
            } else if (element instanceof AbstractElementContainer) {
                collapseRedundantGroups(element)
            }
        }

        // Perform collapse
        toCollapse.each { map ->
            def parent = map.parent as AbstractElementContainer
            def group = map.group as G
            def child = map.child as SvgElement

            // Detach child from group first, then remove group, then add child to parent
            group.element.remove(child.element)
            parent.element.remove(group.element)
            parent.element.add(child.element)
        }
    }

    /**
     * Removes unused definitions from defs element.
     * Note: This is a simplified implementation that removes defs with no references.
     */
    private static void removeUnusedDefinitions(Svg svg) {
        def defs = svg.findAll { it instanceof Defs }

        defs.each { defsElement ->
            def defsChildren = (defsElement as Defs).children
            def usedIds = collectUsedIds(svg)

            def toRemove = defsChildren.findAll { defElement ->
                String id = (defElement as SvgElement).getAttribute('id')
                id && !usedIds.contains(id)
            }

            toRemove.each { (defsElement as Defs).element.remove((it as SvgElement).element) }
        }
    }

    /**
     * Collects all referenced IDs in the SVG.
     */
    private static java.util.Set<String> collectUsedIds(AbstractElementContainer container) {
        java.util.Set<String> ids = [] as java.util.HashSet

        container.children.each { element ->
            if (element instanceof SvgElement) {
                // Check href/xlink:href attributes
                ['href', 'xlink:href'].each { attr ->
                    String href = element.getAttribute(attr)
                    if (href && href.startsWith('#')) {
                        ids.add(href.substring(1))
                    }
                }

                // Check url() references in attributes
                element.getAttributes().each { attr, value ->
                    if (value && value.contains('url(#')) {
                        def matcher = (value =~ /url\(#([^)]+)\)/)
                        matcher.each {
                            ids.add(it[1])
                        }
                    }
                }

                if (element instanceof AbstractElementContainer) {
                    ids.addAll(collectUsedIds(element))
                }
            }
        }

        return ids
    }
}
