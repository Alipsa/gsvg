package se.alipsa.groovy.svg.templates

import groovy.transform.CompileStatic
import se.alipsa.groovy.svg.AbstractElementContainer
import se.alipsa.groovy.svg.SvgElement

/**
 * Abstract base class for creating reusable SVG templates.
 * <p>
 * Templates encapsulate complex SVG patterns that can be applied multiple times
 * with different parameters. Subclasses implement the {@link #apply} method to
 * create the actual SVG structure.
 * <p>
 * <h2>Usage Example:</h2>
 * <pre>
 * class MyTemplate extends Template {
 *     &#64;Override
 *     SvgElement apply(AbstractElementContainer parent, Map params) {
 *         G group = parent.addG()
 *         group.addRect()
 *             .x(params.x ?: 0)
 *             .y(params.y ?: 0)
 *             .width(params.width ?: 100)
 *             .height(params.height ?: 100)
 *             .fill(params.color ?: 'blue')
 *         return group
 *     }
 *
 *     &#64;Override
 *     Map getDefaults() {
 *         return [x: 0, y: 0, width: 100, height: 100, color: 'blue']
 *     }
 * }
 *
 * // Use the template
 * Svg svg = new Svg(400, 400)
 * MyTemplate template = new MyTemplate()
 * template.apply(svg, [x: 50, y: 50, color: 'red'])
 * template.apply(svg, [x: 200, y: 200, width: 150])
 * </pre>
 *
 * @since 0.9.0
 */
@CompileStatic
abstract class Template {

    /**
     * Applies this template to the given parent element with the specified parameters.
     * <p>
     * The parameters map is merged with default values from {@link #getDefaults()}.
     * Template-specific parameters override defaults.
     *
     * @param parent the parent element to add the template content to
     * @param params optional configuration parameters (merged with defaults)
     * @return the root element created by this template
     */
    abstract SvgElement apply(AbstractElementContainer parent, Map params)

    /**
     * Returns the default parameter values for this template.
     * <p>
     * Subclasses should override this method to provide sensible defaults
     * for all configurable parameters.
     *
     * @return map of default parameter values
     */
    Map getDefaults() {
        return [:]
    }

    /**
     * Merges the provided parameters with default values.
     * <p>
     * Parameters explicitly provided take precedence over defaults.
     *
     * @param params user-provided parameters
     * @return merged parameter map
     */
    protected Map mergeParams(Map params) {
        Map defaults = getDefaults()
        Map merged = new HashMap(defaults)
        if (params != null) {
            merged.putAll(params)
        }
        return merged
    }

    /**
     * Applies this template with default parameters.
     *
     * @param parent the parent element
     * @return the root element created by this template
     */
    SvgElement apply(AbstractElementContainer parent) {
        return apply(parent, [:])
    }
}
