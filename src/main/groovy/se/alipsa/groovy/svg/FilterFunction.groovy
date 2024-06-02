package se.alipsa.groovy.svg

abstract class FilterFunction <T extends FilterElement<T>> extends FilterElement<T> {

  enum FilterType {
    identity, table, discrete, linear, gamma
  }

  FilterFunction(SvgElement<? extends SvgElement> parent, String name) {
    super(parent, name)
  }

  T in(String value) {
    addAttribute('in', value)
  }

  String getIn() {
    getAttribute('in')
  }

  T type(String value) {
    addAttribute('type', value)
  }

  /**
   * Indicates the type of component transfer function
   *
   * @param value one of identity, table, discrete, linear, gamma
   * @return the filter function class that the attribute belongs to
   */
  T type(FilterType value) {
    addAttribute('type', value.name())
  }

  String getType() {
    getAt('type')
  }

  /**
   * Attribute when type is table or discrete
   * @param values
   * @return the filter function class that the attribute belongs to
   */
  T tableValues(String values) {
    addAttribute('tableValues', values)
  }

  String getTableValues() {
    getAttribute('tableValues')
  }

  /**
   * Attribute when type is linear
   * @param value
   * @return the filter function class that the attribute belongs to
   */
  T slope(Number value) {
    addAttribute('slope', value)
  }

  String getSlope() {
    getAttribute('slope')
  }

  /**
   * Attribute when type is linear
   * @param value
   * @return the filter function class that the attribute belongs to
   */
  T intercept(Number value) {
    addAttribute('intercept', value)
  }

  String getIntercept() {
    getAttribute('intercept')
  }

  /**
   * Attribute when type is gamma
   * @param value
   * @return the filter function class that the attribute belongs to
   */
  T amplitude(Number value) {
    addAttribute('amplitude', value)
  }

  String getAmplitude() {
    getAttribute('amplitude')
  }

  /**
   * Attribute when type is gamma
   * @param value
   * @return the filter function class that the attribute belongs to
   */
  T exponent(Number value) {
    addAttribute('exponent', value)
  }

  String getExponent() {
    getAttribute('exponent')
  }

  /**
   * Attribute when type is gamma
   * @param value
   * @return the filter function class that the attribute belongs to
   */
  T offset(Number value) {
    addAttribute('offset', value)
  }

  String getOffset() {
    getAttribute('offset')
  }
}
