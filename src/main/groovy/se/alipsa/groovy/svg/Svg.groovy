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
class Svg extends SvgElement<Svg>{
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

  Circle addCircle() {
    return new Circle(this)
  }

  Ellipse addEllipse() {
    return new Ellipse(this)
  }

  Ellipse addEllipse(Number rx, Number ry) {
    return new Ellipse(this, rx, ry)
  }

  G addG() {
    return new G(this)
  }

  Line addLine() {
    return new Line(this)
  }

  Line addLine(Number x1, Number y1, Number x2, Number y2) {
    return new Line(this, x1, y1, x2, y2)
  }

  Path addPath() {
    return new Path(this)
  }

  Polygon addPolygon(Coordinate ... points) {
    return new Polygon(this, points)
  }

  Polygon addPolygon(List<Number>... points) {
    return new Polygon(this, points)
  }

  Polyline addPolyline(Coordinate ... points) {
    return new Polyline(this, points)
  }

  Polyline addPolyline(List<Number>... points) {
    return new Polyline(this, points)
  }

  Rect addRect(Number width, Number height) {
    return new Rect(this, width, height)
  }

  Rect addRect() {
    return new Rect(this)
  }

  Text addText() {
    new Text(this)
  }

  Text addText(String content) {
    new Text(this, content)
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
