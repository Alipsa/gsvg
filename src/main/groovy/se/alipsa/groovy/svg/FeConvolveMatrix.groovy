package se.alipsa.groovy.svg

/**
 * SVG {@code <feConvolveMatrix>} filter primitive that applies a convolution kernel.
 */
class FeConvolveMatrix extends FilterElement<FeConvolveMatrix> {

  static final String NAME='feConvolveMatrix'

  /**
   * Enum for edge mode.
   */
  enum EdgeMode {
    duplicate, wrap, none
  }

  /**
   * Creates a FeConvolveMatrix.
   *
   * @param parent value
   */
  FeConvolveMatrix(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   * Sets the in attribute.
   *
   * @param inStr value
   * @return this element for chaining
   */
  FeConvolveMatrix in(String inStr) {
    addAttribute('in', inStr)
  }

  /**
   * Sets the in attribute.
   *
   * @param inEnum value
   * @return this element for chaining
   */
  FeConvolveMatrix in(In inEnum) {
    addAttribute('in', inEnum.name())
  }

  String getIn() {
    getAttribute('in')
  }

  /**
   * Sets the order attribute.
   *
   * @param order value
   * @return this element for chaining
   */
  FeConvolveMatrix order(Integer order) {
    addAttribute('order', order)
  }

  /**
   * Sets the order attribute.
   *
   * @param order value
   * @return this element for chaining
   */
  FeConvolveMatrix order(String order) {
    addAttribute('order', order)
  }

  String getOrder() {
    getAttribute('order')
  }

  /**
   * Sets the kernel matrix attribute.
   *
   * @param values value
   * @return this element for chaining
   */
  FeConvolveMatrix kernelMatrix(String values) {
    addAttribute('kernelMatrix', values)
  }

  String getKernelMatrix() {
    getAttribute('kernelMatrix')
  }

  /**
   * Sets the divisor attribute.
   *
   * @param value value
   * @return this element for chaining
   */
  FeConvolveMatrix divisor(Number value) {
    addAttribute('divisor', value)
  }

  String getDivisor() {
    getAttribute('divisor')
  }

  /**
   * Sets the bias attribute.
   *
   * @param value value
   * @return this element for chaining
   */
  FeConvolveMatrix bias(Number value) {
    addAttribute('bias', value)
  }

  String getBias() {
    getAttribute('bias')
  }

  /**
   * Sets the target x attribute.
   *
   * @param value value
   * @return this element for chaining
   */
  FeConvolveMatrix targetX(Integer value) {
    addAttribute('targetX', value)
  }

  String getTargetX() {
    getAttribute('targetX')
  }

  /**
   * Sets the target y attribute.
   *
   * @param value value
   * @return this element for chaining
   */
  FeConvolveMatrix targetY(Integer value) {
    addAttribute('targetY', value)
  }

  String getTargetY() {
    getAttribute('targetY')
  }

  /**
   * Sets the edge mode attribute.
   *
   * @param mode value
   * @return this element for chaining
   */
  FeConvolveMatrix edgeMode(EdgeMode mode) {
    addAttribute('edgeMode', mode)
  }

  /**
   * Sets the edge mode attribute.
   *
   * @param mode value
   * @return this element for chaining
   */
  FeConvolveMatrix edgeMode(String mode) {
    addAttribute('edgeMode', mode)
  }

  String getEdgeMode() {
    getAttribute('edgeMode')
  }

  /**
   * Sets the preserve alpha attribute.
   *
   * @param value value
   * @return this element for chaining
   */
  FeConvolveMatrix preserveAlpha(boolean value) {
    addAttribute('preserveAlpha', value ? 'true' : 'false')
  }

  /**
   * Sets the preserve alpha attribute.
   *
   * @param value value
   * @return this element for chaining
   */
  FeConvolveMatrix preserveAlpha(String value) {
    addAttribute('preserveAlpha', value)
  }

  String getPreserveAlpha() {
    getAttribute('preserveAlpha')
  }
}
