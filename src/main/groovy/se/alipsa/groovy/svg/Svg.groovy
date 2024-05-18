package se.alipsa.groovy.svg

import groovy.transform.PackageScope
import org.dom4j.Document
import org.dom4j.Element
import org.dom4j.DocumentHelper

/**
<svg width="${width}" height="${height}" xmlns="${xmlns}">
svgElements.each {
  svg.append(it.toXml()).append('\n')
}
</svg>
 */
class Svg extends AbstractElementContainer<Svg> implements LinearGradientContainer {
  static final String NAME='svg'
  static final String xmlns="http://www.w3.org/2000/svg"

  Svg() {
    super(DocumentHelper.createDocument().addElement('svg', "${xmlns}"))
  }

  Svg(Number w, Number h) {
    this()
    width(w)
    height(h)
  }

  Element width(Number width) {
    element.addAttribute('width', "$width")
  }

  String getWidth() {
    getAttribute('width')
  }

  Svg height(Number height) {
    addAttribute('height', "$height")
  }

  String getHeight() {
    getAttribute('height')
  }

  Document getDocument() {
    element.getDocument()
  }

  Defs addDefs() {
    add(new Defs(this))
  }

  Svg viewBox(String params) {
    addAttribute('viewBox', params)
  }

  Svg addXlink() {
    element.addNamespace(xlinkNs.prefix, xlinkNs.getURI())
    this
  }

  // This is the top element so we return itself as the parent
  @Override
  SvgElement getParent() {
    this
  }
}
