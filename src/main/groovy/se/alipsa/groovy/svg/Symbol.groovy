package se.alipsa.groovy.svg

/**
 * SVG {@code <symbol>} element that defines a reusable graphic not rendered directly.
 */
class Symbol extends AbstractElementContainer<Symbol> {

  static final String NAME='symbol'

  /**
   * Creates a Symbol.
   *
   * @param parent value
   */
  Symbol(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   * Sets the width attribute.
   *
   * @param width value
   * @return this element for chaining
   */
  Symbol width(Number width) {
    addAttribute('width', width)
  }

  /**
   * Sets the width attribute.
   *
   * @param width value
   * @return this element for chaining
   */
  Symbol width(String width) {
    addAttribute('width', width)
  }

  String getWidth() {
    getAttribute('width')
  }

  /**
   * Sets the height attribute.
   *
   * @param height value
   * @return this element for chaining
   */
  Symbol height(Number height) {
    addAttribute('height', height)
  }

  /**
   * Sets the height attribute.
   *
   * @param height value
   * @return this element for chaining
   */
  Symbol height(String height) {
    addAttribute('height', height)
  }

  String getHeight() {
    getAttribute('height')
  }

  /**
   * Sets the x attribute.
   *
   * @param x value
   * @return this element for chaining
   */
  Symbol x(Number x) {
    addAttribute('x', x)
  }

  /**
   * Sets the x attribute.
   *
   * @param x value
   * @return this element for chaining
   */
  Symbol x(String x) {
    addAttribute('x', x)
  }

  String getX() {
    getAttribute('x')
  }

  /**
   * Sets the y attribute.
   *
   * @param y value
   * @return this element for chaining
   */
  Symbol y(Number y) {
    addAttribute('y', y)
  }

  /**
   * Sets the y attribute.
   *
   * @param y value
   * @return this element for chaining
   */
  Symbol y(String y) {
    addAttribute('y', y)
  }

  String getY() {
    getAttribute('y')
  }

  /**
   * Sets the preserve aspect ratio attribute.
   *
   * @param value value
   * @return this element for chaining
   */
  Symbol preserveAspectRatio(String value) {
    addAttribute('preserveAspectRatio', value)
  }

  String getPreserveAspectRatio() {
    getAttribute('preserveAspectRatio')
  }

  /**
   * Sets the ref x attribute.
   *
   * @param refX value
   * @return this element for chaining
   */
  Symbol refX(Number refX) {
    addAttribute('refX', refX)
  }

  String getRefX() {
    getAttribute('refX')
  }

  /**
   * Sets the ref y attribute.
   *
   * @param refY value
   * @return this element for chaining
   */
  Symbol refY(Number refY) {
    addAttribute('refY', refY)
  }

  String getRefY() {
    getAttribute('refY')
  }

  /**
   * Sets the view box attribute.
   *
   * @param params value
   * @return this element for chaining
   */
  Symbol viewBox(String params) {
    addAttribute('viewBox', params)
  }

  String getViewBox() {
    getAttribute('viewBox')
  }
}
