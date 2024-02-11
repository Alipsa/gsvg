package se.alipsa.groovy.svg;

class Rect implements SvgElement  {
  Number width
  Number height

  Number x = 0
  Number y = 0

  Number rx = 0
  Number ry = 0

  String fill

  String style

  Rect(Number width, Number height) {
    this.width = width
    this.height = height
  }

  Rect setX(Number x) {
    this.x = x
    this
  }

  Rect setY(Number y) {
    this.y = y
    this
  }

  Rect setRx(Number rx) {
    this.rx = rx
    this
  }

  Rect setRy(Number ry) {
    this.ry = ry
    this
  }

  Rect setStyle(String style) {
    this.style = style
    this
  }

  Rect setFill(String fill) {
    this.fill = fill
    this
  }

  @Override
  String toXml() {
    return """<rect width="${width}" height="${height}" x="${x}" y="${y}" rx="${rx}" ry="${ry}" ${optionalAttr('fill', fill)} ${optionalAttr('style', style)}/>"""
  }


}
