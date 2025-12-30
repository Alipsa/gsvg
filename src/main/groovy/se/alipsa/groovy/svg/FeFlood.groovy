package se.alipsa.groovy.svg

/**
 * SVG {@code <feFlood>} filter primitive that fills the filter region with a solid color.
 */
class FeFlood extends  FilterElement<FeFlood> {
  static final String NAME = 'feFlood'

  /**
   * Creates a FeFlood.
   *
   * @param parent value
   */
  FeFlood(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   * Sets the flood color attribute.
   *
   * @param color value
   * @return this element for chaining
   */
  FeFlood floodColor(String color) {
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
   * @param opacity value
   * @return this element for chaining
   */
  FeFlood floodOpacity(Number opacity) {
    addAttribute('flood-opacity', opacity)
  }

  /**
   * Sets the flood opacity attribute.
   *
   * @param opacity value
   * @return this element for chaining
   */
  FeFlood floodOpacity(String opacity) {
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
