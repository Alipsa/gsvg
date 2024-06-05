package se.alipsa.groovy.svg

class Mask extends AbstractElementContainer<Mask> {

  static final String NAME='mask'

  Mask(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  Mask width(Number width) {
    addAttribute('width', width)
  }

  Mask width(String width) {
    addAttribute('width', width)
  }

  String getWidth() {
    getAttribute('width')
  }

  Mask height(Number height) {
    addAttribute('height', height)
  }

  Mask height(String height) {
    addAttribute('height', height)
  }

  String getHeight() {
    getAttribute('height')
  }

  Mask x(Number x) {
    addAttribute('x', x)
  }

  Mask x(String x) {
    addAttribute('x', x)
  }

  String getX() {
    getAttribute('x')
  }

  Mask y(Number y) {
    addAttribute('y', y)
  }

  Mask y(String y) {
    addAttribute('y', y)
  }

  String getY() {
    getAttribute('y')
  }

  Mask maskContentUnits(String value) {
    addAttribute('maskContentUnits', value)
  }

  String getMaskContentUnits() {
    getAttribute('maskContentUnits')
  }

  Mask maskUnits(String value) {
    addAttribute('maskUnits', value)
  }

  String maskUnits() {
    getAttribute('maskUnits')
  }
}
