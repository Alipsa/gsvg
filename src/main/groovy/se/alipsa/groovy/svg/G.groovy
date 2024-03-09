package se.alipsa.groovy.svg

/** A container for grouping other SVG elements */
class G extends SvgElement<G> {

    static final String NAME='g'
    /*
    Number fontSize
    String fontFamily
    String fill
    String textAnchor

    List<SvgElement> items = []
    */

    G(SvgElement parent) {
        super(parent, NAME)
    }

}
