package se.alipsa.groovy.svg

class FeColorMatrix extends FilterElement<FeColorMatrix> {
  static final String NAME = 'feColorMatrix'

  enum Type {
    matrix, saturate, hueRotate, luminanceToAlpha
  }

  FeColorMatrix(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  FeColorMatrix in(String inStr) {
    addAttribute('in', inStr)
  }

  FeColorMatrix in(In inEnum) {
    addAttribute('in', inEnum.name())
  }

  String getIn() {
    getAttribute('in')
  }

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

  FeColorMatrix values(String vals) {
    addAttribute('values', vals)
  }

  String values() {
    getAttribute('values')
  }

  FeColorMatrix colorInterpolationFilters(String value) {
    addAttribute('color-interpolation-filters', value)
  }

  String getColorInterpolationFilters() {
    getAttribute('color-interpolation-filters')
  }
}
