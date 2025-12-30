package se.alipsa.groovy.svg

/**
 * SVG {@code <set>} element that performs a discrete animation setting an attribute for a duration.
 */
class Set extends Animation<Set> {

  static final String NAME='set'

  /**
   * Creates a Set.
   *
   * @param parent value
   */
  Set(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   * Sets the attribute name attribute.
   *
   * @param value value
   * @return this element for chaining
   */
  Set attributeName(String value) {
    addAttribute('attributeName', value)
  }

  String getAttributeName() {
    getAttribute('attributeName')
  }

  /**
   * Sets the to attribute.
   *
   * @param value value
   * @return this element for chaining
   */
  Set to(String value) {
    addAttribute('to', value)
  }

  String getTo() {
    getAttribute('to')
  }

  /**
   * Sets the begin attribute.
   *
   * @param value value
   * @return this element for chaining
   */
  Set begin(String value) {
    addAttribute('begin', value)
  }

  String getBegin() {
    getAttribute('begin')
  }

  /**
   * Sets the dur attribute.
   *
   * @param value value
   * @return this element for chaining
   */
  Set dur(String value) {
    addAttribute('dur', value)
  }

  String getDur() {
    getAttribute('dur')
  }
}
