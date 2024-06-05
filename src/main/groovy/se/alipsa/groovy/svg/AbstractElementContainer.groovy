package se.alipsa.groovy.svg

import org.dom4j.Element

abstract class AbstractElementContainer<T extends AbstractElementContainer<T>> extends SvgElement<T> implements ElementContainer {

  AbstractElementContainer(SvgElement<? extends SvgElement> parent, String name) {
    super(parent, name)
  }

  /**
   * Special constructor for Svg as it does not have a parent
   * Use this only to create Svg elements!
   */
  AbstractElementContainer(Element element) {
    super(element)
  }

  A addA() {
    add(new A(this))
  }

  Circle addCircle() {
    add(new Circle(this))
  }

  Circle addCircle(String id) {
    add(new Circle(this).id(id))
  }

  ClipPath addClipPath() {
    add(new ClipPath(this))
  }

  Ellipse addEllipse() {
    add(new Ellipse(this))
  }

  Ellipse addEllipse(Number rx, Number ry) {
    add(new Ellipse(this, rx, ry))
  }

  Filter addFilter() {
    add(new Filter(this))
  }

  Filter addFilter(String id) {
    add(new Filter(this).id(id))
  }

  G addG() {
    add(new G(this))
  }

  Image addImage() {
    add(new Image(this))
  }

  Line addLine() {
    add(new Line(this))
  }

  Line addLine(Number x1, Number y1, Number x2, Number y2) {
    add(new Line(this, x1, y1, x2, y2))
  }

  Mask addMask() {
    add(new Mask(this))
  }

  Mask addMask(String id) {
    add(new Mask(this).id(id))
  }

  Path addPath() {
    add(new Path(this))
  }

  Path addPath(String id) {
    addPath().id(id)
  }

  Polygon addPolygon(String points) {
    add(new Polygon(this).points(points))
  }

  Polygon addPolygon(Coordinate ... points) {
    add(new Polygon(this, points))
  }

  Polygon addPolygon(List<Number>... points) {
    add(new Polygon(this, points))
  }

  Polyline addPolyline(Coordinate ... points) {
    add(new Polyline(this, points))
  }

  Polyline addPolyline(List<Number>... points) {
    add(new Polyline(this, points))
  }

  Rect addRect(Number width, Number height) {
    add(new Rect(this, width, height))
  }

  Rect addRect() {
    add(new Rect(this))
  }

  Text addText() {
    add(new Text(this))
  }

  Text addText(String content) {
    add(new Text(this, content))
  }

  Use addUse() {
    add(new Use(this))
  }
}
