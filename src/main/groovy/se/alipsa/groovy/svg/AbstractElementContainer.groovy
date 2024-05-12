package se.alipsa.groovy.svg

import org.dom4j.Element

abstract class AbstractElementContainer<T extends AbstractElementContainer<T>> extends SvgElement<T> {


    List<SvgElement<? extends SvgElement>> children = []

    AbstractElementContainer(SvgElement<? extends SvgElement> parent, String name) {
        super(parent, name)
    }

    /**
     * Special constructor for Svg asit does not have a parent
     * Use this only to create Svg elements!
     */
    AbstractElementContainer(Element element) {
        super(element)
    }

    SvgElement getAt(Integer index) {
        children[index]
    }

    List<SvgElement> getAt(String name) {
        children.stream().filter (e -> e.name == name).findAll()
    }

    List<SvgElement> getAt(Class<? extends SvgElement> clazz) {
        children.stream().filter (e -> e.class == clazz).findAll()
    }

    A addA() {
        add(new A(this))
    }

    Circle addCircle() {
        add(new Circle(this))
    }

    Ellipse addEllipse() {
        add(new Ellipse(this))
    }

    Ellipse addEllipse(Number rx, Number ry) {
        add(new Ellipse(this, rx, ry))
    }

    G addG() {
        add(new G(this))
    }

    Line addLine() {
        add(new Line(this))
    }

    Line addLine(Number x1, Number y1, Number x2, Number y2) {
        add(new Line(this, x1, y1, x2, y2))
    }

    Path addPath() {
        add(new Path(this))
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

    ClipPath addClipPath() {
        add(new ClipPath(this))
    }

    Use addUse() {
        add(new Use(this))
    }

    <E extends SvgElement> E add(E svgElement) {
        children.add(svgElement)
        svgElement
    }
}