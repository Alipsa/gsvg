package se.alipsa.groovy.svg

/**
 * SVG {@code <switch>} element that conditionally selects one child to render.
 */
class Switch extends AbstractElementContainer<Switch> {
  static final String NAME='switch'

  /**
   * Creates a Switch.
   *
   * @param parent value
   */
  Switch(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   * Sets the system language attribute.
   *
   * @param value value
   * @return this element for chaining
   */
  Switch systemLanguage(String value) {
    addAttribute('systemLanguage', value)
  }

  String getSystemLanguage() {
    getAttribute('systemLanguage')
  }

  /**
   * Sets the required features attribute.
   *
   * @param value value
   * @return this element for chaining
   */
  Switch requiredFeatures(String value) {
    addAttribute('requiredFeatures', value)
  }

  String getRequiredFeatures() {
    getAttribute('requiredFeatures')
  }

  /**
   * Sets the required extensions attribute.
   *
   * @param value value
   * @return this element for chaining
   */
  Switch requiredExtensions(String value) {
    addAttribute('requiredExtensions', value)
  }

  String getRequiredExtensions() {
    getAttribute('requiredExtensions')
  }
}
