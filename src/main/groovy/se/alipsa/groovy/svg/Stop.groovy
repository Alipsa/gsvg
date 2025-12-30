package se.alipsa.groovy.svg

/**
 * SVG {@code <stop>} element that defines a color stop in a gradient.
 */
class Stop extends SvgElement<Stop> {

  static final String NAME = 'stop'

  /**
   * Creates a Stop.
   *
   * @param parent value
   */
  Stop(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   * Sets the offset attribute.
   *
   * @param offset value
   * @return this element for chaining
   */
  Stop offset(String offset) {
    addAttribute("offset", offset)
  }

  /**
   * Sets the offset attribute.
   *
   * @param offset value
   * @return this element for chaining
   */
  Stop offset(Number offset) {
    addAttribute("offset", offset)
  }

  /**
   * Returns the offset value.
   *
   * @return the offset value
   */
  String getOffset() {
    getAttribute("offset")
  }

  /**
   * Sets the stop color attribute.
   *
   * @param color value
   * @return this element for chaining
   */
  Stop stopColor(String color) {
    addAttribute("stop-color", color)
  }

  /**
   * Returns the stop color value.
   *
   * @return the stop color value
   */
  String getStopColor() {
    getAttribute("stop-color")
  }
}
