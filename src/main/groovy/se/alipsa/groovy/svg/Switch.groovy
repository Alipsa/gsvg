package se.alipsa.groovy.svg

/**
 * SVG {@code <switch>} element that conditionally selects one child to render.
 */
class Switch extends AbstractElementContainer<Switch> {
  static final String NAME='switch'

  /**
   * Creates a Switch.
   *
   * @param parent the parent SVG element
   */
  Switch(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   * Sets the system language attribute.
   *
   * @param value the value
   * @return this element for chaining
   */
  Switch systemLanguage(String value) {
    addAttribute('systemLanguage', value)
  }

  /**
   * Returns the system language value.
   *
   * @return the system language value
   */
  String getSystemLanguage() {
    getAttribute('systemLanguage')
  }

  /**
   * Sets the required features attribute.
   *
   * @param value the value
   * @return this element for chaining
   */
  Switch requiredFeatures(String value) {
    addAttribute('requiredFeatures', value)
  }

  /**
   * Returns the required features value.
   *
   * @return the required features value
   */
  String getRequiredFeatures() {
    getAttribute('requiredFeatures')
  }

  /**
   * Sets the required extensions attribute.
   *
   * @param value the value
   * @return this element for chaining
   */
  Switch requiredExtensions(String value) {
    addAttribute('requiredExtensions', value)
  }

  /**
   * Returns the required extensions value.
   *
   * @return the required extensions value
   */
  String getRequiredExtensions() {
    getAttribute('requiredExtensions')
  }
}
