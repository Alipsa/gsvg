package se.alipsa.groovy.svg

class ForeignObject extends SvgElement<ForeignObject> implements Animatable<ForeignObject>{

  static final String NAME='foreignObject'

  List<ForeignElement> foreignElements = []

  ForeignObject(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  ForeignObject width(Number width) {
    addAttribute('width', width)
  }

  ForeignObject width(String width) {
    addAttribute('width', width)
  }

  String getWidth() {
    getAttribute('width')
  }

  ForeignObject height(Number height) {
    addAttribute('height', height)
  }

  ForeignObject height(String height) {
    addAttribute('height', height)
  }

  String getHeight() {
    getAttribute('height')
  }

  ForeignObject x(Number x) {
    addAttribute('x', x)
  }

  String getX() {
    getAttribute('x')
  }

  ForeignObject y(Number y) {
    addAttribute('y', y)
  }

  ForeignObject y(String y) {
    addAttribute('y', y)
  }

  String getY() {
    getAttribute('y')
  }

  ForeignObject addContent(String content) {
    element.addText(content)
    this
  }

  ForeignElement addElement(String qName) {
    ForeignElement e = new ForeignElement(this, qName, "http://www.w3.org/1999/xhtml")
    foreignElements << e
    e
  }
}
