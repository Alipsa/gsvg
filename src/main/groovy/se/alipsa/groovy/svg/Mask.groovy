package se.alipsa.groovy.svg

/**
 * SVG {@code <mask>} element that defines a mask for transparency effects.
 */
class Mask extends AbstractElementContainer<Mask> implements Animatable<Mask> {

  static final String NAME='mask'

  /**
   * Creates a Mask.
   *
   * @param parent value
   */
  Mask(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   * Sets the width attribute.
   *
   * @param width value
   * @return this element for chaining
   */
  Mask width(Number width) {
    addAttribute('width', width)
  }

  /**
   * Sets the width attribute.
   *
   * @param width value
   * @return this element for chaining
   */
  Mask width(String width) {
    addAttribute('width', width)
  }

  /**
   * Returns the width value.
   *
   * @return the width value
   */
  String getWidth() {
    getAttribute('width')
  }

  /**
   * Sets the height attribute.
   *
   * @param height value
   * @return this element for chaining
   */
  Mask height(Number height) {
    addAttribute('height', height)
  }

  /**
   * Sets the height attribute.
   *
   * @param height value
   * @return this element for chaining
   */
  Mask height(String height) {
    addAttribute('height', height)
  }

  /**
   * Returns the height value.
   *
   * @return the height value
   */
  String getHeight() {
    getAttribute('height')
  }

  /**
   * Sets the x attribute.
   *
   * @param x value
   * @return this element for chaining
   */
  Mask x(Number x) {
    addAttribute('x', x)
  }

  /**
   * Sets the x attribute.
   *
   * @param x value
   * @return this element for chaining
   */
  Mask x(String x) {
    addAttribute('x', x)
  }

  /**
   * Returns the x value.
   *
   * @return the x value
   */
  String getX() {
    getAttribute('x')
  }

  /**
   * Sets the y attribute.
   *
   * @param y value
   * @return this element for chaining
   */
  Mask y(Number y) {
    addAttribute('y', y)
  }

  /**
   * Sets the y attribute.
   *
   * @param y value
   * @return this element for chaining
   */
  Mask y(String y) {
    addAttribute('y', y)
  }

  /**
   * Returns the y value.
   *
   * @return the y value
   */
  String getY() {
    getAttribute('y')
  }

  /**
   * Sets the mask content units attribute.
   *
   * @param value value
   * @return this element for chaining
   */
  Mask maskContentUnits(String value) {
    addAttribute('maskContentUnits', value)
  }

  /**
   * Returns the mask content units value.
   *
   * @return the mask content units value
   */
  String getMaskContentUnits() {
    getAttribute('maskContentUnits')
  }

  /**
   * Sets the mask units attribute.
   *
   * @param value value
   * @return this element for chaining
   */
  Mask maskUnits(String value) {
    addAttribute('maskUnits', value)
  }

  /**
   * Mask units.
   *
   * @return the result
   */
  String maskUnits() {
    getAttribute('maskUnits')
  }
}
