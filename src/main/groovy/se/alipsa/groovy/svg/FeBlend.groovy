package se.alipsa.groovy.svg

/**
 * SVG {@code <feBlend>} filter primitive that blends two input images.
 */
class FeBlend extends FilterElement<FeBlend> {

  static final String NAME = 'feBlend'


  /**
   * Enum for mode.
   */
  enum Mode {
    normal, multiply, screen, darken, lighten;
  }

  /**
   * Creates a FeBlend.
   *
   * @param parent the parent SVG element
   */
  FeBlend(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   * Sets the in attribute.
   *
   * @param inStr the input source string identifier
   * @return this element for chaining
   */
  FeBlend in(String inStr) {
    addAttribute('in', inStr)
  }

  /**
   * Sets the in attribute.
   *
   * @param inEnum the input source enumeration
   * @return this element for chaining
   */
  FeBlend in(In inEnum) {
    addAttribute('in', inEnum.name())
  }

  /**
   * Returns the in value.
   *
   * @return the in value
   */
  String getIn() {
    getAttribute('in')
  }

  /**
   * Sets the in 2 attribute.
   *
   * @param inStr the input source string identifier
   * @return this element for chaining
   */
  FeBlend in2(String inStr) {
    addAttribute('in2', inStr)
  }

  /**
   * Returns the in 2 value.
   *
   * @return the in 2 value
   */
  String getIn2() {
    getAttribute('in2')
  }

  /**
   * Sets the mode attribute.
   *
   * @param mode the mode
   * @return this element for chaining
   */
  FeBlend mode(String mode) {
    addAttribute('mode', mode)
  }

  /**
   * Sets the mode attribute.
   *
   * @param mode the mode
   * @return this element for chaining
   */
  FeBlend mode(Mode mode) {
    addAttribute('mode', mode.name())
  }

  /**
   * Returns the mode value.
   *
   * @return the mode value
   */
  String getMode() {
    getAttribute('mode')
  }
}
