package se.alipsa.groovy.svg

class Pattern extends AbstractElementContainer<Pattern> {

  static final String NAME='pattern'

  Pattern(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  Pattern x(Number x) {
    addAttribute('x', String.valueOf(x))
  }

  String getX() {
    getAttribute('x')
  }

  Pattern y(Number y) {
    addAttribute('y', String.valueOf(y))
  }

  String getY() {
    getAttribute('y')
  }

  Pattern width(Number width) {
    addAttribute('width', "$width")
  }

  String getWidth() {
    getAttribute('width')
  }

  Pattern height(Number height) {
    addAttribute('height', "$height")
  }

  String getHeight() {
    getAttribute('height')
  }

  Pattern patternUnits(String units) {
    addAttribute('patternUnits', units)
  }

  String getPatternUnits() {
    getAttribute('patternUnits')
  }
}
