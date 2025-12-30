package se.alipsa.groovy.svg

import groovy.transform.CompileStatic

/**
 * SVG {@code <feComposite>} filter primitive that composites two input images.
 */
@CompileStatic
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
   * @param parent the parent SVG element
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
   * @param value the value
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
   * @param inStr the input source string identifier
   * @return this element for chaining
   */
  FeComposite "in"(String inStr) {
    addAttribute('in', inStr)
  }

  /**
   * Sets the in attribute.
   *
   * @param inEnum the input source enumeration
   * @return this element for chaining
   */
  FeComposite "in"(In inEnum) {
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

  /** value for the arithmetic operator */
  FeComposite k1(String value) {
    addAttribute('k1', value)
  }

  /** value for the arithmetic operator */
  FeComposite k2(Number value) {
    addAttribute('k2', value)
  }

  /**
   * Returns the k 1 value.
   *
   * @return the k 1 value
   */
  String getK1() {
    getAttribute('k1')
  }

  /**
   * Sets the k 2 value for the arithmetic operator.
   *
   * @param value the k 2 value
   * @return the current {@link FeComposite} instance
   */
  FeComposite k2(String value) {
    addAttribute('k2', value)
  }

  /**
   * Sets the k 3 value for the arithmetic operator.
   *
   * @param value the k 3 value
   * @return the current {@link FeComposite} instance
   */
  FeComposite k3(Number value) {
    addAttribute('k3', value)
  }

  /**
   * Returns the k 2 value.
   *
   * @return the k 2 value
   */
  String getK2() {
    getAttribute('k2')
  }

  /**
   * Sets the k 3 value used by the arithmetic operator.
   *
   * @param value the k 3 value
   * @return this filter element
   */
  FeComposite k3(String value) {
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
  FeComposite k4(String value) {
    addAttribute('k4', value)
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
