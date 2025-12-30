package se.alipsa.groovy.svg

/** A container for grouping other SVG elements */
/**
 * SVG {@code <g>} element that groups graphics for shared attributes or transforms.
 */
class G extends AbstractElementContainer<G> implements GradientContainer, Animatable<G> {

    static final String NAME='g'
    /*
    Number fontSize
    String fontFamily
    String fill
    String textAnchor

    List<SvgElement> items = []
    */

    String getName() {
        return NAME
    }

    /**
     * Creates a G.
     *
     * @param parent value
     */
    G(SvgElement parent) {
        super(parent, NAME)
    }

    /**
     * Sets the fill attribute.
     *
     * @param fill value
     * @return this element for chaining
     */
    G fill(String fill) {
        addAttribute('fill', String.valueOf(fill))
    }

    /**
     * Returns the fill value.
     *
     * @return the fill value
     */
    String getFill() {
        getAttribute('fill')
    }

    /**
     * Sets the stroke attribute.
     *
     * @param stroke value
     * @return this element for chaining
     */
    G stroke(String stroke) {
        addAttribute('stroke', "$stroke")
    }

    /**
     * Returns the stroke value.
     *
     * @return the stroke value
     */
    String getStroke() {
        getAttribute('stroke')
    }

    /**
     * Sets the stroke width attribute.
     *
     * @param strokeWidth value
     * @return this element for chaining
     */
    G strokeWidth(Number strokeWidth) {
        addAttribute('stroke-width', "$strokeWidth")
    }

    /**
     * Returns the stroke width value.
     *
     * @return the stroke width value
     */
    String getStrokeWidth() {
        getAttribute('stroke-width')
    }

    /**
     * Sets the transform attribute.
     *
     * @param transformation value
     * @return this element for chaining
     */
    G transform(String transformation) {
        addAttribute('transform', transformation)
    }

    /**
     * Returns the transform value.
     *
     * @return the transform value
     */
    String getTransform() {
        getAttribute('transform')
    }

    /**
     * Sets the font size attribute.
     *
     * @param size value
     * @return this element for chaining
     */
    G fontSize(Number size) {
        addAttribute('font-size', size)
    }

    /**
     * Sets the font size attribute.
     *
     * @param size value
     * @return this element for chaining
     */
    G fontSize(String size) {
        addAttribute('font-size', size)
    }

    /**
     * Returns the font size value.
     *
     * @return the font size value
     */
    String getFontSize() {
        getAttribute('font-size')
    }

    /**
     * Sets the font family attribute.
     *
     * @param family value
     * @return this element for chaining
     */
    G fontFamily(String family) {
        addAttribute('font-family', family)
    }

    /**
     * Returns the font family value.
     *
     * @return the font family value
     */
    String getFontFamily() {
        getAttribute('font-family')
    }

    /**
     * Sets the font weight attribute.
     *
     * @param weight value
     * @return this element for chaining
     */
    G fontWeight(String weight) {
        addAttribute('font-weight', weight)
    }

    /**
     * Returns the font weight value.
     *
     * @return the font weight value
     */
    String getFontWeight() {
        getAttribute('font-weight')
    }
}
