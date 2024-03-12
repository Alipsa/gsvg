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
class Svg extends AbstractElementContainer<Svg>{
  static final String xmlns="http://www.w3.org/2000/svg"

  @PackageScope
  Svg() {
    super(DocumentHelper.createDocument().addElement('svg', "${xmlns}"))
  }

  Svg(Number width, Number height) {
    this()
    setWidth(width)
    setHeight(height)
  }

  @PackageScope
  Element setWidth(Number width) {
    element.addAttribute('width', "$width")
  }

  @PackageScope
  Element setHeight(Number height) {
    element.addAttribute('height', "${height}")
  }

  Document getDocument() {
    return element.getDocument()
  }

  // This is the top element so we return itself as the parent
  @Override
  SvgElement getParent() {
    return this
  }
}
