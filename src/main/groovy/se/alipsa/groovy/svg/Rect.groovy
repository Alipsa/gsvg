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

  Rect x(Number x) {
    this.x = x
    this
  }

  Rect y(Number y) {
    this.y = y
    this
  }

  Rect rx(Number rx) {
    this.rx = rx
    this
  }

  Rect ry(Number ry) {
    this.ry = ry
    this
  }

  Rect style(String style) {
    this.style = style
    this
  }

  Rect fill(String fill) {
    this.fill = fill
    this
  }

  @Override
  String toXml() {
    return """<rect width="${width}" height="${height}" x="${x}" y="${y}" rx="${rx}" ry="${ry}" ${optionalAttr('fill', fill)} ${optionalAttr('style', style)}/>"""
  }


}
