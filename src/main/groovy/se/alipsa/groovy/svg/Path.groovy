package se.alipsa.groovy.svg;

class Path implements SvgElement {

  String id
  String d
  String style

  Path id(String id) {
    this.id = id
    this
  }

  Path d(String d) {
    this.d = d
    this
  }

  Path style(String style) {
    this.style = style
    this
  }

  @Override
  String toXml() {
    """<path ${optionalAttr('id', id)} d="$d" ${optionalAttr('style', style)} />"""
  }
}
