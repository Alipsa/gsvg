package se.alipsa.groovy.svg

/**
 * SVG {@code <feGaussianBlur>} filter primitive that applies a Gaussian blur.
 */
class FeGaussianBlur extends FilterElement<FeGaussianBlur> {

  static final String NAME = 'feGaussianBlur'

  /**
   * Creates a FeGaussianBlur.
   *
   * @param parent value
   */
  FeGaussianBlur(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   * Sets the in attribute.
   *
   * @param input value
   * @return this element for chaining
   */
  FeGaussianBlur in(String input) {
    addAttribute('in', input)
  }

  String getIn() {
    getAttribute('in')
  }

  /**
   * Sets the std deviation attribute.
   *
   * @param deviation value
   * @return this element for chaining
   */
  FeGaussianBlur stdDeviation(Number deviation) {
    addAttribute('stdDeviation', deviation)
  }

  String getStdDeviation() {
    getAttribute('stdDeviation')
  }
}
