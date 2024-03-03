package se.alipsa.groovy.svg

import org.dom4j.Document
import org.dom4j.Element
import org.dom4j.DocumentHelper

class Svg {
  Number width
  Number height
  String xmlns="http://www.w3.org/2000/svg"
  Document doc
  Element root

  Svg(Number width, Number height) {
    this.width = width
    this.height = height
    doc = DocumentHelper.createDocument()
    root = doc.addElement('svg', "${xmlns}")
        .addAttribute('width', "$width")
        .addAttribute('height', "${height}")
  }

  Circle addCircle() {
    return new Circle(root)
  }

  Ellipse addEllipse(Number rx, Number ry) {
    return new Ellipse(root, rx, ry)
  }

  Line addLine(Number x1, Number y1, Number x2, Number y2) {
    return new Line(root, x1, y1, x2, y2)
  }

  Path addPath() {
    return new Path(root)
  }

  Polygon addPolygon(Coordinate ... points) {
    return new Polygon(root, points)
  }

  Polygon addPolygon(List<Number>... points) {
    return new Polygon(root, points)
  }

  Polyline addPolyline(Coordinate ... points) {
    return new Polyline(root, points)
  }

  Polyline addPolyline(List<Number>... points) {
    return new Polyline(root, points)
  }

  Rect addRect(Number width, Number height) {
    return new Rect(root, width, height)
  }

  Text addText() {
    new Text(root)
  }

  Text addText(String content) {
    new Text(root, content)
  }

  Document getDocument() {
    return doc
  }


  String toXml() {
    return root.asXML()
    /*
    def svg = new StringBuilder("""<svg width="${width}" height="${height}" xmlns="${xmlns}">\n  """)
    svgElements.each {
      svg.append(it.toXml()).append('\n')
    }
    svg.append('</svg>')
     */
  }

}
