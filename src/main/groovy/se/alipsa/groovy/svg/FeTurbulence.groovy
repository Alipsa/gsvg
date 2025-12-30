package se.alipsa.groovy.svg

/**
 * SVG {@code <feTurbulence>} filter primitive that generates noise or turbulence.
 */
class FeTurbulence extends FilterElement<FeTurbulence> {

  static final String NAME = 'feTurbulence'

  /**
   * Creates a FeTurbulence.
   *
   * @param parent value
   */
  FeTurbulence(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   * Sets the type attribute.
   *
   * @param type value
   * @return this element for chaining
   */
  FeTurbulence type(String type) {
    addAttribute('type', type)
  }

  String getType() {
    getAttribute('type')
  }

  /**
   * Sets the base frequency attribute.
   *
   * @param value value
   * @return this element for chaining
   */
  FeTurbulence baseFrequency(Number value) {
    addAttribute('baseFrequency', value)
  }

  String getBaseFrequency() {
    getAttribute('baseFrequency')
  }

  /**
   * Sets the num octaves attribute.
   *
   * @param value value
   * @return this element for chaining
   */
  FeTurbulence numOctaves(Number value) {
    addAttribute('numOctaves', value)
  }


}
