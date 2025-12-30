package se.alipsa.groovy.svg

/**
 * SVG {@code <view>} element that defines a named view with viewBox settings.
 */
class View extends SvgElement<View> {

  static final String NAME = 'view'

  /**
   * Creates a View.
   *
   * @param parent the parent SVG element
   */
  View(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   * Sets the view box attribute.
   *
   * @param value the value
   * @return this element for chaining
   */
  View viewBox(String value) {
    addAttribute('viewBox', value)
  }

  /**
   * Returns the view box value.
   *
   * @return the view box value
   */
  String getViewBox() {
    getAttribute('viewBox')
  }

  /**
   * Sets the preserve aspect ratio attribute.
   *
   * @param value the value
   * @return this element for chaining
   */
  View preserveAspectRatio(String value) {
    addAttribute('preserveAspectRatio', value)
  }

  /**
   * Returns the preserve aspect ratio value.
   *
   * @return the preserve aspect ratio value
   */
  String getPreserveAspectRatio() {
    getAttribute('preserveAspectRatio')
  }
}
