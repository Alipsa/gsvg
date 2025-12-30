package se.alipsa.groovy.svg

/**
 * SVG {@code <view>} element that defines a named view with viewBox settings.
 */
class View extends SvgElement<View> {

  static final String NAME = 'view'

  /**
   * Creates a View.
   *
   * @param parent value
   */
  View(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   * Sets the view box attribute.
   *
   * @param value value
   * @return this element for chaining
   */
  View viewBox(String value) {
    addAttribute('viewBox', value)
  }

  String getViewBox() {
    getAttribute('viewBox')
  }

  /**
   * Sets the preserve aspect ratio attribute.
   *
   * @param value value
   * @return this element for chaining
   */
  View preserveAspectRatio(String value) {
    addAttribute('preserveAspectRatio', value)
  }

  String getPreserveAspectRatio() {
    getAttribute('preserveAspectRatio')
  }
}
