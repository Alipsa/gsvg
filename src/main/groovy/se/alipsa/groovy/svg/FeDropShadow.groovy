package se.alipsa.groovy.svg

class FeDropShadow extends FilterElement<FeDropShadow> {

  static final String NAME = 'feDropShadow'

  FeDropShadow(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /** The horizontal shift position for the offset */
  FeDropShadow dx(Number dx) {
    addAttribute('dx', "$dx")
  }

  String getDx() {
    getAttribute('dx')
  }

  /** The vertical shift position for the offset*/
  FeDropShadow dy(Number dy) {
    addAttribute('dy', "$dy")
  }

  String getDy() {
    getAttribute('dy')
  }

  FeDropShadow stdDeviation(Number deviation) {
    addAttribute('stdDeviation', deviation)
  }

  String getStdDeviation() {
    getAttribute('stdDeviation')
  }

  FeDropShadow floodColor(String color) {
    addAttribute('flood-color', color)
  }

  String getFloodColor() {
    getAttribute('flood-color')
  }

  FeDropShadow floodOpacity(Number opacity) {
    addAttribute('flood-opacity', opacity)
  }

  String getFloodOpacity() {
    getAttribute('flood-opacity')
  }
}
