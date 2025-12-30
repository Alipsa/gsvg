package se.alipsa.groovy.svg

/**
 * SVG {@code <feColorMatrix>} filter primitive that applies a color matrix to RGBA values.
 */
class FeColorMatrix extends FilterElement<FeColorMatrix> {
  static final String NAME = 'feColorMatrix'

  /**
   * Enum for type.
   */
  enum Type {
    matrix, saturate, hueRotate, luminanceToAlpha
  }

  /**
   * Creates a FeColorMatrix.
   *
   * @param parent the parent SVG element
   */
  FeColorMatrix(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   * Sets the in attribute.
   *
   * @param inStr the input source string identifier
   * @return this element for chaining
   */
  FeColorMatrix in(String inStr) {
    addAttribute('in', inStr)
  }

  /**
   * Sets the in attribute.
   *
   * @param inEnum the input source enumeration
   * @return this element for chaining
   */
  FeColorMatrix in(In inEnum) {
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
   * Sets the type attribute.
   *
   * @param value the value
   * @return this element for chaining
   */
  FeColorMatrix type(String value) {
    addAttribute('type', value)
  }

  /**
   * Indicates the type of component transfer function
   *
   * @param value one of identity, table, discrete, linear, gamma
   * @return the filter function class that the attribute belongs to
   */
  FeColorMatrix type(Type value) {
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
   * Sets the values attribute.
   *
   * @param vals the values
   * @return this element for chaining
   */
  FeColorMatrix values(String vals) {
    addAttribute('values', vals)
  }

  /**
   * Values.
   *
   * @return the result
   */
  String values() {
    getAttribute('values')
  }

  /**
   * Sets the color interpolation filters attribute.
   *
   * @param value the value
   * @return this element for chaining
   */
  FeColorMatrix colorInterpolationFilters(String value) {
    addAttribute('color-interpolation-filters', value)
  }

  /**
   * Returns the color interpolation filters value.
   *
   * @return the color interpolation filters value
   */
  String getColorInterpolationFilters() {
    getAttribute('color-interpolation-filters')
  }
}
