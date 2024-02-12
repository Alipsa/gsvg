package se.alipsa.groovy.svg

/** A container for grouping other SVG elements */
class G {

    Number fontSize
    String fontFamily
    String fill
    String textAnchor

    List<SvgElement> items = []
}
