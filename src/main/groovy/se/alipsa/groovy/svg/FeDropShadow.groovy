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

  /**
   * Sets the horizontal shift position for the offset.
   *
   * @param dx the horizontal shift position for the offset
   * @return this element for chaining
   */
  FeDropShadow dx(String dx) {
    addAttribute('dx', "$dx")
  }

  /** The vertical shift position for the offset*/
  FeDropShadow dy(Number dy) {
    addAttribute('dy', "$dy")
  }

  /**
   * Returns the dx value.
   *
   * @return the dx value
   */
  String getDx() {
    getAttribute('dx')
  }

  /** The vertical shift position for the offset*/
  FeDropShadow dy(String dy) {
    addAttribute('dy', "$dy")
  }
  /**
   * Returns the dy value.
   *
   * @return the dy value
   */
  String getDy() {
    getAttribute('dy')
  }

  /**
   * Sets the std deviation attribute.
   *
   * @param deviation the standard deviation for blur
   * @return this element for chaining
   */
  FeDropShadow stdDeviation(Number deviation) {
    addAttribute('stdDeviation', deviation)
  }

  /**
   * Sets the std deviation attribute.
   *
   * @param deviation value
   * @return this element for chaining
   */
  FeDropShadow stdDeviation(String deviation) {
    addAttribute('stdDeviation', deviation)
  }
  /**
   * Returns the std deviation value.
   *
   * @return the std deviation value
   */
  String getStdDeviation() {
    getAttribute('stdDeviation')
  }

  /**
   * Sets the flood color attribute.
   *
   * @param color the color
   * @return this element for chaining
   */
  FeDropShadow floodColor(String color) {
    addAttribute('flood-color', color)
  }

  /**
   * Returns the flood color value.
   *
   * @return the flood color value
   */
  String getFloodColor() {
    getAttribute('flood-color')
  }

  /**
   * Sets the flood opacity attribute.
   *
   * @param opacity the opacity value (0.0 to 1.0)
   * @return this element for chaining
   */
  FeDropShadow floodOpacity(Number opacity) {
    addAttribute('flood-opacity', opacity)
  }

  /**
   * Sets the flood opacity attribute.
   *
   * @param opacity value
   * @return this element for chaining
   */
  FeDropShadow floodOpacity(String opacity) {
    addAttribute('flood-opacity', opacity)
  }
  /**
   * Returns the flood opacity value.
   *
   * @return the flood opacity value
   */
  String getFloodOpacity() {
    getAttribute('flood-opacity')
  }
}
