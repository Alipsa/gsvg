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
   * @param parent value
   */
  FeBlend(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   * Sets the in attribute.
   *
   * @param inStr value
   * @return this element for chaining
   */
  FeBlend in(String inStr) {
    addAttribute('in', inStr)
  }

  /**
   * Sets the in attribute.
   *
   * @param inEnum value
   * @return this element for chaining
   */
  FeBlend in(In inEnum) {
    addAttribute('in', inEnum.name())
  }

  String getIn() {
    getAttribute('in')
  }

  /**
   * Sets the in 2 attribute.
   *
   * @param inStr value
   * @return this element for chaining
   */
  FeBlend in2(String inStr) {
    addAttribute('in2', inStr)
  }

  String getIn2() {
    getAttribute('in2')
  }

  /**
   * Sets the mode attribute.
   *
   * @param mode value
   * @return this element for chaining
   */
  FeBlend mode(String mode) {
    addAttribute('mode', mode)
  }

  /**
   * Sets the mode attribute.
   *
   * @param mode value
   * @return this element for chaining
   */
  FeBlend mode(Mode mode) {
    addAttribute('mode', mode.name())
  }

  String getMode() {
    getAttribute('mode')
  }
}
