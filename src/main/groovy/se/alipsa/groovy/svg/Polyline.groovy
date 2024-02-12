package se.alipsa.groovy.svg;

class Polyline implements SvgElement {
  List<Coordinate> points = []
  String style

  Polyline(Coordinate... points) {
    this.points = points as List<Coordinate>
  }

  Polyline(List<Number>... points) {
    this.points = points.collect({it as Coordinate})
  }

  Polyline points(Coordinate... points) {
    this.points = points as List<Coordinate>
    this
  }

  Polyline points(List<Number>... points) {
    this.points = points.collect({it as Coordinate})
    this
  }

  Polyline addPoints(Coordinate... points) {
    this.points.addAll(points)
    this
  }

  Polyline addPoint(Coordinate point) {
    this.points << point
    this
  }

  Polyline style(String style) {
    this.style = style
    this
  }

  @Override
  String toXml() {
    """<polyline points="${points.collect({it.toString()}).join(' ')}" ${optionalAttr('style', style)} />"""
  }
}
