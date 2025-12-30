package se.alipsa.groovy.svg

/**
 * Base class for SVG filter primitives that process inputs and produce results.
 */
abstract class FilterElement<T extends SvgElement<T>> extends SvgElement<T> {

  /**
   * Creates a FilterElement.
   *
   * @param parent value
   * @param name value
   */
  FilterElement(SvgElement<? extends SvgElement> parent, String name) {
    super(parent, name)
  }

  /**
   * Sets the result attribute.
   *
   * @param result value
   * @return this element for chaining
   */
  T result(String result) {
    addAttribute('result', result)
  }

  /**
   * Returns the result value.
   *
   * @return the result value
   */
  String getResult() {
    getAttribute('result')
  }

  /**
   * Sets the x attribute.
   *
   * @param x value
   * @return this element for chaining
   */
  T x(Number x) {
    addAttribute('x', String.valueOf(x))
  }

  /**
   * Sets the x attribute.
   *
   * @param x value
   * @return this element for chaining
   */
  T x(String x) {
    addAttribute('x', String.valueOf(x))
  }
  /**
   * Returns the x value.
   *
   * @return the x value
   */
  String getX() {
    getAttribute('x')
  }

  /**
   * Sets the y attribute.
   *
   * @param y value
   * @return this element for chaining
   */
  T y(Number y) {
    addAttribute('y', String.valueOf(y))
  }

  /**
   * Sets the y attribute.
   *
   * @param y value
   * @return this element for chaining
   */
  T y(String y) {
    addAttribute('y', String.valueOf(y))
  }
  /**
   * Returns the y value.
   *
   * @return the y value
   */
  String getY() {
    getAttribute('y')
  }

  /**
   * Sets the width attribute.
   *
   * @param width value
   * @return this element for chaining
   */
  T width(Number width) {
    addAttribute('width', "$width")
  }

  /**
   * Sets the width attribute.
   *
   * @param width value
   * @return this element for chaining
   */
  T width(String width) {
    addAttribute('width', width)
  }

  /**
   * Returns the width value.
   *
   * @return the width value
   */
  String getWidth() {
    getAttribute('width')
  }

  /**
   * Sets the height attribute.
   *
   * @param height value
   * @return this element for chaining
   */
  T height(Number height) {
    addAttribute('height', "$height")
  }

  /**
   * Sets the height attribute.
   *
   * @param height value
   * @return this element for chaining
   */
  T height(String height) {
    addAttribute('height', height)
  }

  /**
   * Returns the height value.
   *
   * @return the height value
   */
  String getHeight() {
    getAttribute('height')
  }
}
