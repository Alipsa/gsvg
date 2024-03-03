package se.alipsa.groovy.svg

import org.dom4j.Element

/** A container for grouping other SVG elements */
class G extends SvgElement<G> {

    Number fontSize
    String fontFamily
    String fill
    String textAnchor

    List<SvgElement> items = []

    G(Element parent) {
        super(parent.addElement("g"))
    }

}
