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
   * @param parent value
   */
  FeColorMatrix(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   * Sets the in attribute.
   *
   * @param inStr value
   * @return this element for chaining
   */
  FeColorMatrix in(String inStr) {
    addAttribute('in', inStr)
  }

  /**
   * Sets the in attribute.
   *
   * @param inEnum value
   * @return this element for chaining
   */
  FeColorMatrix in(In inEnum) {
    addAttribute('in', inEnum.name())
  }

  String getIn() {
    getAttribute('in')
  }

  /**
   * Sets the type attribute.
   *
   * @param value value
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

  String getType() {
    getAt('type')
  }

  /**
   * Sets the values attribute.
   *
   * @param vals value
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
   * @param value value
   * @return this element for chaining
   */
  FeColorMatrix colorInterpolationFilters(String value) {
    addAttribute('color-interpolation-filters', value)
  }

  String getColorInterpolationFilters() {
    getAttribute('color-interpolation-filters')
  }
}
