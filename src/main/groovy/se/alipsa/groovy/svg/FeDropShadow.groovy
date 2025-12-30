package se.alipsa.groovy.svg

/**
 * SVG {@code <feDropShadow>} filter primitive that creates a drop shadow.
 */
class FeDropShadow extends FilterElement<FeDropShadow> {

  static final String NAME = 'feDropShadow'

  /**
   * Creates a FeDropShadow.
   *
   * @param parent value
   */
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

  /**
   * Sets the std deviation attribute.
   *
   * @param deviation value
   * @return this element for chaining
   */
  FeDropShadow stdDeviation(Number deviation) {
    addAttribute('stdDeviation', deviation)
  }

  String getStdDeviation() {
    getAttribute('stdDeviation')
  }

  /**
   * Sets the flood color attribute.
   *
   * @param color value
   * @return this element for chaining
   */
  FeDropShadow floodColor(String color) {
    addAttribute('flood-color', color)
  }

  String getFloodColor() {
    getAttribute('flood-color')
  }

  /**
   * Sets the flood opacity attribute.
   *
   * @param opacity value
   * @return this element for chaining
   */
  FeDropShadow floodOpacity(Number opacity) {
    addAttribute('flood-opacity', opacity)
  }

  String getFloodOpacity() {
    getAttribute('flood-opacity')
  }
}
