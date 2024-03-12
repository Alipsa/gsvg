package se.alipsa.groovy.svg

import org.dom4j.Element

abstract class AbstractElementContainer<T extends AbstractElementContainer<T>> extends SvgElement<T> {


    AbstractElementContainer(SvgElement<? extends SvgElement> parent, String name) {
        super(parent, name)
    }

    AbstractElementContainer(Element element) {
        super(element)
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
}
