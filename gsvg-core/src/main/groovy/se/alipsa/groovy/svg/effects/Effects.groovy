package se.alipsa.groovy.svg.effects

import groovy.transform.CompileStatic
import se.alipsa.groovy.svg.*

/**
 * Provides preset filter effects for common visual effects.
 * <p>
 * This utility class offers factory methods for creating commonly used SVG filter effects
 * such as drop shadows, glows, blurs, and color transformations. Each method returns a
 * configured {@link Filter} that can be added to an SVG's {@code <defs>} section.
 * <p>
 * <h2>Usage Examples:</h2>
 * <pre>
 * // Create a drop shadow effect
 * Svg svg = new Svg(200, 200)
 * Filter shadow = Effects.dropShadow(svg.addDefs(), id: 'shadow1', dx: 2, dy: 2, blur: 3)
 * svg.addCircle().cx(100).cy(100).r(50).filter('url(#shadow1)')
 *
 * // Create a glow effect
 * Filter glow = Effects.glow(svg.addDefs(), id: 'glow1', color: 'yellow', strength: 2)
 * svg.addText('Glowing!').x(50).y(100).filter('url(#glow1)')
 *
 * // Create a blur effect
 * Filter blur = Effects.blur(svg.addDefs(), 5.0, 'blur1')
 * svg.addRect().width(100).height(100).filter('url(#blur1)')
 * </pre>
 *
 * @since 0.9.0
 */
@CompileStatic
class Effects {

    /**
     * Creates a drop shadow filter effect.
     * <p>
     * Adds a realistic drop shadow behind an element using feDropShadow or
     * a combination of feGaussianBlur, feOffset, and feMerge for wider compatibility.
     * <p>
     * Options:
     * <ul>
     *   <li>id - Filter ID (default: 'dropShadow')</li>
     *   <li>dx - Horizontal offset in pixels (default: 2)</li>
     *   <li>dy - Vertical offset in pixels (default: 2)</li>
     *   <li>blur - Blur radius (stdDeviation) (default: 3)</li>
     *   <li>opacity - Shadow opacity 0-1 (default: 0.5)</li>
     *   <li>color - Shadow color (default: 'black')</li>
     * </ul>
     *
     * @param parent parent element (typically a Defs)
     * @param options optional configuration map
     * @return configured Filter
     */
    static Filter dropShadow(AbstractElementContainer parent, Map options = [:]) {
        String id = options.id ?: 'dropShadow'
        Number dx = options.dx != null ? options.dx as Number : 2
        Number dy = options.dy != null ? options.dy as Number : 2
        Number blur = options.blur != null ? options.blur as Number : 3
        Number opacity = options.opacity != null ? options.opacity as Number : 0.5
        String color = options.color ?: 'black'

        Filter filter = parent.addFilter(id)

        // Use feDropShadow for modern browsers (simpler)
        filter.addFeDropShadow()
            .dx(dx)
            .dy(dy)
            .stdDeviation(blur)
            .floodOpacity(opacity)
            .floodColor(color)

        filter
    }

    /**
     * Creates a glow/halo effect around an element.
     * <p>
     * Uses feGaussianBlur and feComposite to create a colored glow effect.
     * <p>
     * Options:
     * <ul>
     *   <li>id - Filter ID (default: 'glow')</li>
     *   <li>color - Glow color (default: 'yellow')</li>
     *   <li>blur - Blur radius (default: 4)</li>
     *   <li>strength - Intensity multiplier 1-5 (default: 2)</li>
     * </ul>
     *
     * @param parent parent element (typically a Defs)
     * @param options optional configuration map
     * @return configured Filter
     */
    static Filter glow(AbstractElementContainer parent, Map options = [:]) {
        String id = options.id ?: 'glow'
        String color = options.color ?: 'yellow'
        Number blur = options.blur != null ? options.blur as Number : 4
        Number strength = options.strength != null ? options.strength as Number : 2

        Filter filter = parent.addFilter(id)

        // Create colored flood
        filter.addFeFlood()
            .floodColor(color)
            .result('flood')

        // Composite with source alpha to get the shape
        filter.addFeComposite()
            .in('flood')
            .in2('SourceAlpha')
            .operator('in')
            .result('coloredAlpha')

        // Blur the colored shape
        filter.addFeGaussianBlur()
            .in('coloredAlpha')
            .stdDeviation(blur)
            .result('blurred')

        // Merge the glow with the original
        FeMerge merge = filter.addFeMerge()
        // Optionally add multiple glow layers for strength
        int layers = Math.min(5, Math.max(1, strength.intValue()))
        layers.times {
            merge.addFeMergeNode().in('blurred')
        }
        merge.addFeMergeNode().in('SourceGraphic')

        filter
    }

    /**
     * Creates a simple Gaussian blur filter.
     * <p>
     * Applies uniform blur to the element.
     *
     * @param parent parent element (typically a Defs)
     * @param blur blur radius (stdDeviation), default 4.0
     * @param id filter ID, default 'blur'
     * @return configured Filter
     */
    static Filter blur(AbstractElementContainer parent, double blur = 4.0, String id = 'blur') {
        Filter filter = parent.addFilter(id)
        filter.addFeGaussianBlur()
            .in('SourceGraphic')
            .stdDeviation(blur)
        filter
    }

    /**
     * Creates a grayscale (desaturation) filter.
     * <p>
     * Converts the element to grayscale using feColorMatrix.
     *
     * @param parent parent element (typically a Defs)
     * @param id filter ID, default 'grayscale'
     * @return configured Filter
     */
    static Filter grayscale(AbstractElementContainer parent, String id = 'grayscale') {
        Filter filter = parent.addFilter(id)
        filter.addFeColorMatrix()
            .type('saturate')
            .values('0')  // 0 = fully desaturated (grayscale)
        filter
    }

    /**
     * Creates a sepia tone filter.
     * <p>
     * Applies a warm, vintage sepia tone effect using feColorMatrix.
     *
     * @param parent parent element (typically a Defs)
     * @param id filter ID, default 'sepia'
     * @return configured Filter
     */
    static Filter sepia(AbstractElementContainer parent, String id = 'sepia') {
        Filter filter = parent.addFilter(id)
        // Sepia tone matrix: warm brown tones
        filter.addFeColorMatrix()
            .type('matrix')
            .values('0.393 0.769 0.189 0 0 ' +
                    '0.349 0.686 0.168 0 0 ' +
                    '0.272 0.534 0.131 0 0 ' +
                    '0 0 0 1 0')
        filter
    }

    /**
     * Creates a brightness adjustment filter.
     * <p>
     * Makes the element lighter or darker.
     * <p>
     * Options:
     * <ul>
     *   <li>id - Filter ID (default: 'brightness')</li>
     *   <li>amount - Brightness adjustment, 0 = black, 1 = normal, >1 = brighter (default: 1.5)</li>
     * </ul>
     *
     * @param parent parent element (typically a Defs)
     * @param options optional configuration map
     * @return configured Filter
     */
    static Filter brightness(AbstractElementContainer parent, Map options = [:]) {
        String id = options.id ?: 'brightness'
        Number amountNum = options.amount != null ? options.amount as Number : 1.5
        double amount = amountNum.doubleValue()

        Filter filter = parent.addFilter(id)
        FeComponentTransfer transfer = filter.addFeComponentTransfer()
        transfer.addFeFuncR().type('linear').slope(amount)
        transfer.addFeFuncG().type('linear').slope(amount)
        transfer.addFeFuncB().type('linear').slope(amount)

        filter
    }

    /**
     * Creates a contrast adjustment filter.
     * <p>
     * Increases or decreases contrast.
     * <p>
     * Options:
     * <ul>
     *   <li>id - Filter ID (default: 'contrast')</li>
     *   <li>amount - Contrast multiplier, <1 = less contrast, >1 = more contrast (default: 1.5)</li>
     * </ul>
     *
     * @param parent parent element (typically a Defs)
     * @param options optional configuration map
     * @return configured Filter
     */
    static Filter contrast(AbstractElementContainer parent, Map options = [:]) {
        String id = options.id ?: 'contrast'
        Number amountNum = options.amount != null ? options.amount as Number : 1.5
        double amount = amountNum.doubleValue()
        double intercept = -(0.5 * amount) + 0.5

        Filter filter = parent.addFilter(id)
        FeComponentTransfer transfer = filter.addFeComponentTransfer()
        transfer.addFeFuncR().type('linear').slope(amount).intercept(intercept)
        transfer.addFeFuncG().type('linear').slope(amount).intercept(intercept)
        transfer.addFeFuncB().type('linear').slope(amount).intercept(intercept)

        filter
    }

    /**
     * Applies a filter to an element by setting its filter attribute.
     * <p>
     * Convenience method to apply a filter reference to any SVG element.
     *
     * @param element the element to apply the filter to
     * @param filterId the ID of the filter (without 'url(#)' wrapper)
     * @return the element for method chaining
     */
    static <T extends SvgElement<T>> T applyFilter(T element, String filterId) {
        element.filter("url(#${filterId})")
        element
    }

    /**
     * Applies a filter to an element by setting its filter attribute.
     * <p>
     * Convenience method that accepts a Filter object and extracts its ID.
     *
     * @param element the element to apply the filter to
     * @param filter the Filter to apply
     * @return the element for method chaining
     */
    static <T extends SvgElement<T>> T applyFilter(T element, Filter filter) {
        element.filter("url(#${filter.getId()})")
        element
    }
}
