package se.alipsa.groovy.svg

import groovy.transform.CompileStatic

/**
 * SVG {@code <set>} element that performs a discrete animation setting an attribute for a duration.
 */
@CompileStatic
class Set extends Animation<Set> {

  static final String NAME='set'

  /**
   * Creates a Set.
   *
   * @param parent the parent SVG element
   */
  Set(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   * Sets the attribute name attribute.
   *
   * @param value the value
   * @return this element for chaining
   */
  Set attributeName(String value) {
    addAttribute('attributeName', value)
  }

  /**
   * Returns the attribute name value.
   *
   * @return the attribute name value
   */
  String getAttributeName() {
    getAttribute('attributeName')
  }

  /**
   * Sets the to attribute.
   *
   * @param value the value
   * @return this element for chaining
   */
  Set to(String value) {
    addAttribute('to', value)
  }

  /**
   * Returns the to value.
   *
   * @return the to value
   */
  String getTo() {
    getAttribute('to')
  }

  /**
   * Sets the begin attribute.
   *
   * @param value the value
   * @return this element for chaining
   */
  Set begin(String value) {
    addAttribute('begin', value)
  }

  /**
   * Returns the begin value.
   *
   * @return the begin value
   */
  String getBegin() {
    getAttribute('begin')
  }

  /**
   * Sets the dur attribute.
   *
   * @param value the value
   * @return this element for chaining
   */
  Set dur(String value) {
    addAttribute('dur', value)
  }

  /**
   * Returns the dur value.
   *
   * @return the dur value
   */
  String getDur() {
    getAttribute('dur')
  }
}
