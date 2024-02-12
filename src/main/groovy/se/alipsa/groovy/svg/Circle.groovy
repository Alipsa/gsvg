package se.alipsa.groovy.svg;

class Circle implements SvgElement {

  Number cx
  Number cy
  Number r
  String stroke
  Number strokeWidth
  String fill

  Circle cx(Number cx) {
    this.cx = cx
    this
  }

  Circle cy(Number cy) {
    this.cy = cy
    this
  }

  Circle r(Number r) {
    this.r = r
    this
  }

  Circle stroke(String stroke) {
    this.stroke = stroke
    this
  }

  Circle strokeWidth(Number strokeWidth) {
    this.strokeWidth = strokeWidth
    this
  }

  Circle fill(String fill) {
    this.fill = fill
    this
  }

  @Override
  String toXml() {
    """<circle ${optionalAttr('cx', cx)} ${optionalAttr('cy', cy)} r="${r}" ${optionalAttr('stroke',stroke)} ${optionalAttr('stroke-width', strokeWidth)} ${optionalAttr('fill', fill)} />"""
  }

  @Override
  String toString() {
    toXml()
  }
}
