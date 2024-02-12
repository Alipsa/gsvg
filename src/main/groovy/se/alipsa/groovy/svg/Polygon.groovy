package se.alipsa.groovy.svg;

class Polygon implements SvgElement {

  List<Coordinate> points = []
  String style

  Polygon(Coordinate... points) {
    this.points = points as List<Coordinate>
  }

  Polygon(List<Number>... points) {
    this.points = points.collect({it as Coordinate})
  }

  Polygon points(Coordinate... points) {
    this.points = points as List<Coordinate>
    this
  }

  Polygon points(List<Number>... points) {
    this.points = points.collect({it as Coordinate})
    this
  }

  Polygon addPoints(Coordinate... points) {
    this.points.addAll(points)
    this
  }

  Polygon addPoint(Coordinate point) {
    this.points << point
    this
  }

  Polygon style(String style) {
    this.style = style
    this
  }

  @Override
  String toXml() {
    """<polygon points="${points.collect({it.toString()}).join(' ')}" ${optionalAttr('style', style)} />"""
  }
}
