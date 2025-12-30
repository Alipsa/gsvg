package se.alipsa.groovy.svg

/**
 * Base class for component transfer functions used by feComponentTransfer.
 */
abstract class FilterFunction <T extends FilterElement<T>> extends FilterElement<T> {

  /**
   * Enum for filter type.
   */
  enum FilterType {
    identity, table, discrete, linear, gamma
  }

  /**
   * Creates a FilterFunction.
   *
   * @param parent the parent SVG element
   * @param name the name of the element
   */
  FilterFunction(SvgElement<? extends SvgElement> parent, String name) {
    super(parent, name)
  }

  /**
   * Sets the in attribute.
   *
   * @param value the value
   * @return this element for chaining
   */
  T in(String value) {
    addAttribute('in', value)
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
   * Sets the type attribute.
   *
   * @param value the value
   * @return this element for chaining
   */
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

  /**
   * Returns the type value.
   *
   * @return the type value
   */
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

  /**
   * Returns the table values value.
   *
   * @return the table values value
   */
  String getTableValues() {
    getAttribute('tableValues')
  }

  /**
   * Attribute when type is linear
   * @param value the slope value
   * @return the filter function class that the attribute belongs to
   */
  T slope(Number value) {
    addAttribute('slope', value)
  }

  /**
   * Attribute when type is linear
   * @param value
   * @return the filter function class that the attribute belongs to
   */
  T slope(String value) {
    addAttribute('slope', value)
  }
  /**
   * Returns the slope value.
   *
   * @return the slope value
   */
  String getSlope() {
    getAttribute('slope')
  }

  /**
   * Attribute when type is linear
   * @param value the intercept value
   * @return the filter function class that the attribute belongs to
   */
  T intercept(Number value) {
    addAttribute('intercept', value)
  }

  /**
   * Attribute when type is linear
   * @param value
   * @return the filter function class that the attribute belongs to
   */
  T intercept(String value) {
    addAttribute('intercept', value)
  }
  /**
   * Returns the intercept value.
   *
   * @return the intercept value
   */
  String getIntercept() {
    getAttribute('intercept')
  }

  /**
   * Attribute when type is gamma
   * @param value the amplitude value
   * @return the filter function class that the attribute belongs to
   */
  T amplitude(Number value) {
    addAttribute('amplitude', value)
  }

  /**
   * Attribute when type is gamma
   * @param value
   * @return the filter function class that the attribute belongs to
   */
  T amplitude(String value) {
    addAttribute('amplitude', value)
  }
  /**
   * Returns the amplitude value.
   *
   * @return the amplitude value
   */
  String getAmplitude() {
    getAttribute('amplitude')
  }

  /**
   * Attribute when type is gamma
   * @param value the exponent value
   * @return the filter function class that the attribute belongs to
   */
  T exponent(Number value) {
    addAttribute('exponent', value)
  }

  /**
   * Attribute when type is gamma
   * @param value
   * @return the filter function class that the attribute belongs to
   */
  T exponent(String value) {
    addAttribute('exponent', value)
  }
  /**
   * Returns the exponent value.
   *
   * @return the exponent value
   */
  String getExponent() {
    getAttribute('exponent')
  }

  /**
   * Attribute when type is gamma
   * @param value the offset value
   * @return the filter function class that the attribute belongs to
   */
  T offset(Number value) {
    addAttribute('offset', value)
  }

  /**
   * Attribute when type is gamma
   * @param value
   * @return the filter function class that the attribute belongs to
   */
  T offset(String value) {
    addAttribute('offset', value)
  }
  /**
   * Returns the offset value.
   *
   * @return the offset value
   */
  String getOffset() {
    getAttribute('offset')
  }
}
