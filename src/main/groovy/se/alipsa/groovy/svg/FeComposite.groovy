package se.alipsa.groovy.svg

/**
 * SVG {@code <feComposite>} filter primitive that composites two input images.
 */
class FeComposite extends FilterElement<FeComposite> {

  static final String NAME = 'feComposite'

  /**
   * Enum for operator.
   */
  enum Operator {
    over, in, out, atop, xor, lighter, arithmetic
  }

  /**
   * Creates a FeComposite.
   *
   * @param parent value
   */
  FeComposite(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   *
   * @param value, one of over | in | out | atop | xor | lighter | arithmetic
   * @return
   */
  FeComposite operator(String value) {
    addAttribute('operator', value)
  }

  /**
   * Sets the operator attribute.
   *
   * @param value value
   * @return this element for chaining
   */
  FeComposite operator(Operator value) {
    addAttribute('operator', value.name())
  }

  /**
   * Returns the operator value.
   *
   * @return the operator value
   */
  String getOperator() {
    getAttribute('operator')
  }

  /**
   * Sets the in attribute.
   *
   * @param inStr value
   * @return this element for chaining
   */
  FeComposite in(String inStr) {
    addAttribute('in', inStr)
  }

  /**
   * Sets the in attribute.
   *
   * @param inEnum value
   * @return this element for chaining
   */
  FeComposite in(In inEnum) {
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
   * @param inStr value
   * @return this element for chaining
   */
  FeComposite in2(String inStr) {
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

  /** value for the arithmetic operator */
  FeComposite k1(Number value) {
    addAttribute('k1', value)
  }

  /**
   * Returns the k 1 value.
   *
   * @return the k 1 value
   */
  String getK1() {
    getAttribute('k1')
  }

  /** value for the arithmetic operator */
  FeComposite k2(Number value) {
    addAttribute('k2', value)
  }

  /**
   * Returns the k 2 value.
   *
   * @return the k 2 value
   */
  String getK2() {
    getAttribute('k2')
  }

  /** value for the arithmetic operator */
  FeComposite k3(Number value) {
    addAttribute('k3', value)
  }

  /**
   * Returns the k 3 value.
   *
   * @return the k 3 value
   */
  String getK3() {
    getAttribute('k3')
  }

  /** value for the arithmetic operator */
  FeComposite k4(Number value) {
    addAttribute('k4', value)
  }

  /**
   * Returns the k 4 value.
   *
   * @return the k 4 value
   */
  String getK4() {
    getAttribute('k4')
  }
}
