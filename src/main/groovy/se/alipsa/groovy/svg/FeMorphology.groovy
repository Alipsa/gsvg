package se.alipsa.groovy.svg

/**
 * SVG {@code <feMorphology>} filter primitive that erodes or dilates the input image.
 */
class FeMorphology extends FilterElement<FeMorphology> {

  static final String NAME='feMorphology'

  /**
   * Enum for operator.
   */
  enum Operator {
    erode, dilate
  }

  /**
   * Creates a FeMorphology.
   *
   * @param parent value
   */
  FeMorphology(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   * Sets the operator attribute.
   *
   * @param mode value
   * @return this element for chaining
   */
  FeMorphology operator(String mode) {
    addAttribute('operator', mode)
  }

  /**
   * Sets the operator attribute.
   *
   * @param mode value
   * @return this element for chaining
   */
  FeMorphology operator(Operator mode) {
    addAttribute('operator', mode.name())
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
  FeMorphology in(String inStr) {
    addAttribute('in', inStr)
  }

  /**
   * Sets the in attribute.
   *
   * @param inEnum value
   * @return this element for chaining
   */
  FeMorphology in(In inEnum) {
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
   * Sets the radius attribute.
   *
   * @param value value
   * @return this element for chaining
   */
  FeMorphology radius(Number value) {
    addAttribute('radius', value)
  }

  /**
   * Sets the radius attribute.
   *
   * @param value value
   * @return this element for chaining
   */
  FeMorphology radius(String value) {
    addAttribute('radius', value)
  }

  /**
   * Returns the radius value.
   *
   * @return the radius value
   */
  String getRadius() {
    getAttribute('radius')
  }
}
