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
   * @param parent the parent SVG element
   */
  FeConvolveMatrix(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   * Sets the in attribute.
   *
   * @param inStr the input source string identifier
   * @return this element for chaining
   */
  FeConvolveMatrix in(String inStr) {
    addAttribute('in', inStr)
  }

  /**
   * Sets the in attribute.
   *
   * @param inEnum the input source enumeration
   * @return this element for chaining
   */
  FeConvolveMatrix in(In inEnum) {
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
   * Sets the order attribute.
   *
   * @param order the order of the convolution matrix
   * @return this element for chaining
   */
  FeConvolveMatrix order(Integer order) {
    addAttribute('order', order)
  }

  /**
   * Sets the order attribute.
   *
   * @param order the order of the convolution matrix
   * @return this element for chaining
   */
  FeConvolveMatrix order(String order) {
    addAttribute('order', order)
  }

  /**
   * Returns the order value.
   *
   * @return the order value
   */
  String getOrder() {
    getAttribute('order')
  }

  /**
   * Sets the kernel matrix attribute.
   *
   * @param values the convolution kernel matrix values
   * @return this element for chaining
   */
  FeConvolveMatrix kernelMatrix(String values) {
    addAttribute('kernelMatrix', values)
  }

  /**
   * Returns the kernel matrix value.
   *
   * @return the kernel matrix value
   */
  String getKernelMatrix() {
    getAttribute('kernelMatrix')
  }

  /**
   * Sets the divisor attribute.
   *
   * @param value the value
   * @return this element for chaining
   */
  FeConvolveMatrix divisor(Number value) {
    addAttribute('divisor', value)
  }

  /**
   * Returns the divisor value.
   *
   * @return the divisor value
   */
  String getDivisor() {
    getAttribute('divisor')
  }

  /**
   * Sets the bias attribute.
   *
   * @param value the value
   * @return this element for chaining
   */
  FeConvolveMatrix bias(Number value) {
    addAttribute('bias', value)
  }

  /**
   * Returns the bias value.
   *
   * @return the bias value
   */
  String getBias() {
    getAttribute('bias')
  }

  /**
   * Sets the target x attribute.
   *
   * @param value the value
   * @return this element for chaining
   */
  FeConvolveMatrix targetX(Integer value) {
    addAttribute('targetX', value)
  }

  /**
   * Returns the target x value.
   *
   * @return the target x value
   */
  String getTargetX() {
    getAttribute('targetX')
  }

  /**
   * Sets the target y attribute.
   *
   * @param value the value
   * @return this element for chaining
   */
  FeConvolveMatrix targetY(Integer value) {
    addAttribute('targetY', value)
  }

  /**
   * Returns the target y value.
   *
   * @return the target y value
   */
  String getTargetY() {
    getAttribute('targetY')
  }

  /**
   * Sets the edge mode attribute.
   *
   * @param mode the mode
   * @return this element for chaining
   */
  FeConvolveMatrix edgeMode(EdgeMode mode) {
    addAttribute('edgeMode', mode)
  }

  /**
   * Sets the edge mode attribute.
   *
   * @param mode the mode
   * @return this element for chaining
   */
  FeConvolveMatrix edgeMode(String mode) {
    addAttribute('edgeMode', mode)
  }

  /**
   * Returns the edge mode value.
   *
   * @return the edge mode value
   */
  String getEdgeMode() {
    getAttribute('edgeMode')
  }

  /**
   * Sets the preserve alpha attribute.
   *
   * @param value the value
   * @return this element for chaining
   */
  FeConvolveMatrix preserveAlpha(boolean value) {
    addAttribute('preserveAlpha', value ? 'true' : 'false')
  }

  /**
   * Sets the preserve alpha attribute.
   *
   * @param value the value
   * @return this element for chaining
   */
  FeConvolveMatrix preserveAlpha(String value) {
    addAttribute('preserveAlpha', value)
  }

  /**
   * Returns the preserve alpha value.
   *
   * @return the preserve alpha value
   */
  String getPreserveAlpha() {
    getAttribute('preserveAlpha')
  }
}
