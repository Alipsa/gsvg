package se.alipsa.groovy.svg;

/**
 * SVG {@code <linearGradient>} element that defines a linear gradient paint.
 */
class LinearGradient extends Gradient<LinearGradient> {

  static final String NAME = 'linearGradient'

  /**
   * Creates a LinearGradient.
   *
   * @param parent value
   */
  LinearGradient(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   * Sets the x 1 attribute.
   *
   * @param x1 value
   * @return this element for chaining
   */
  LinearGradient x1(String x1) {
    addAttribute('x1', x1)
  }

  /**
   * Sets the x 1 attribute.
   *
   * @param x1 value
   * @return this element for chaining
   */
  LinearGradient x1(Number x1) {
    addAttribute('x1', x1)
  }

  String getX1() {
    getAttribute('x1')
  }

  /**
   * Sets the x 2 attribute.
   *
   * @param x2 value
   * @return this element for chaining
   */
  LinearGradient x2(String x2) {
    addAttribute('x2', x2)
  }

  /**
   * Sets the x 2 attribute.
   *
   * @param x2 value
   * @return this element for chaining
   */
  LinearGradient x2(Number x2) {
    addAttribute('x2', x2)
  }

  String getX2() {
    getAttribute('x2')
  }

  /**
   * Sets the y 1 attribute.
   *
   * @param y1 value
   * @return this element for chaining
   */
  LinearGradient y1(String y1) {
    addAttribute('y1', y1)
  }

  /**
   * Sets the y 1 attribute.
   *
   * @param y1 value
   * @return this element for chaining
   */
  LinearGradient y1(Number y1) {
    addAttribute('y1', y1)
  }

  String getY1() {
    getAttribute('y1')
  }

  /**
   * Sets the y 2 attribute.
   *
   * @param y2 value
   * @return this element for chaining
   */
  LinearGradient y2(String y2) {
    addAttribute('y2', y2)
  }

  /**
   * Sets the y 2 attribute.
   *
   * @param y2 value
   * @return this element for chaining
   */
  LinearGradient y2(Number y2) {
    addAttribute('y2', y2)
  }

  String getY2() {
    getAttribute('y2')
  }

}
