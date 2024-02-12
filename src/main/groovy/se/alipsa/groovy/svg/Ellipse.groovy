package se.alipsa.groovy.svg;

class Ellipse implements SvgElement  {

  Number rx
  Number ry
  Number cx
  Number cy
  String style

  Ellipse(Number rx, Number ry) {
    this.rx = rx
    this.ry = ry
  }

  Ellipse cx(Number cx) {
    this.cx = cx
    this
  }

  Ellipse cy(Number cy) {
    this.cy = cy
    this
  }

  Ellipse style(String style) {
    this.style = style
    this
  }

  @Override
  String toXml() {
    """<ellipse rx="${rx}" ry="${ry}" ${optionalAttr('cx', cx)} ${optionalAttr('cy', cy)} ${optionalAttr('style', style)} />"""
  }
}
