package se.alipsa.groovy.svg;

class Line implements SvgElement {

  /** The start of the line on the x-axis */
  Number x1

  /** The start of the line on the y-axis */
  Number y1

  /** The end of the line on the x-axis */
  Number x2

  /** The end of the line on the y-axis */
  Number y2

  String style

  Line(Number x1, Number y1, Number x2, Number y2) {
    this.x1 = x1
    this.y1 = y1
    this.x2 = x2
    this.y2 = y2
  }

  Line style(String style) {
    this.style = style
    this
  }

  @Override
  String toXml() {
    """<line x1="$x1" y1="$y1" x2="$x2" y2="$y2" ${optionalAttr('style', style)} />"""
  }
}
