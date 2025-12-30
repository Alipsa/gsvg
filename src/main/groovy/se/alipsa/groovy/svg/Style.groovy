package se.alipsa.groovy.svg

/**
 * SVG {@code <style>} element that defines CSS rules for the document.
 */
class Style extends StringContentContainer<Style> {

  static final String NAME='style'

  /**
   * Creates a Style.
   *
   * @param parent value
   */
  Style(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   * Sets the type attribute.
   *
   * @param value value
   * @return this element for chaining
   */
  Style type(String value) {
    addAttribute('type', value)
  }

  String getType() {
    getAttribute('type')
  }
}
