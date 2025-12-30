package se.alipsa.groovy.svg

/**
 * SVG {@code <feTile>} filter primitive that tiles the input image.
 */
class FeTile extends FilterElement<FeTile> {

  static final String NAME = 'feTile'

  /**
   * Creates a FeTile.
   *
   * @param parent value
   */
  FeTile(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   * Sets the in attribute.
   *
   * @param inStr value
   * @return this element for chaining
   */
  FeTile in(String inStr) {
    addAttribute('in', inStr)
  }

  /**
   * Sets the in attribute.
   *
   * @param inEnum value
   * @return this element for chaining
   */
  FeTile in(In inEnum) {
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
}
