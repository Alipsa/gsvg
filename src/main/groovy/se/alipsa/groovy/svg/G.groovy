package se.alipsa.groovy.svg

/** A container for grouping other SVG elements */
class G extends AbstractElementContainer<G> implements GradientContainer {

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

    G(SvgElement parent) {
        super(parent, NAME)
    }

    G fill(String fill) {
        addAttribute('fill', String.valueOf(fill))
    }

    String getFill() {
        getAttribute('fill')
    }

    G stroke(String stroke) {
        addAttribute('stroke', "$stroke")
    }

    String getStroke() {
        getAttribute('stroke')
    }

    G strokeWidth(Number strokeWidth) {
        addAttribute('stroke-width', "$strokeWidth")
    }

    String getStrokeWidth() {
        getAttribute('stroke-width')
    }

    G transform(String transformation) {
        addAttribute('transform', transformation)
    }

    String getTransform() {
        getAttribute('transform')
    }

    G fontSize(Number size) {
        addAttribute('font-size', size)
    }

    String getFontSize() {
        getAttribute('font-size')
    }

    G fontFamily(String family) {
        addAttribute('font-family', family)
    }

    String getFontFamily() {
        getAttribute('font-family')
    }

    G fontWeight(String weight) {
        addAttribute('font-weight', weight)
    }

    String getFontWeight() {
        getAttribute('font-weight')
    }
}
