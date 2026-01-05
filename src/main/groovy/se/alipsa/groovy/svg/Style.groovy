package se.alipsa.groovy.svg

import groovy.transform.CompileStatic

/**
 * SVG {@code <style>} element that defines CSS rules for the document.
 */
@CompileStatic
class Style extends StringContentContainer<Style> {

  static final String NAME='style'

  /**
   * Creates a Style.
   *
   * @param parent the parent SVG element
   */
  Style(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   * Sets the type attribute.
   *
   * @param value the value
   * @return this element for chaining
   */
  Style type(String value) {
    addAttribute('type', value)
  }

  /**
   * Returns the type value.
   *
   * @return the type value
   */
  String getType() {
    getAttribute('type')
  }
}
