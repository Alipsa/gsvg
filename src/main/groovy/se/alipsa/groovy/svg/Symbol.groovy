package se.alipsa.groovy.svg

/**
 * SVG {@code <symbol>} element that defines a reusable graphic not rendered directly.
 */
class Symbol extends AbstractElementContainer<Symbol> {

  static final String NAME='symbol'

  /**
   * Creates a Symbol.
   *
   * @param parent the parent SVG element
   */
  Symbol(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   * Sets the width attribute.
   *
   * @param width the width
   * @return this element for chaining
   */
  Symbol width(Number width) {
    addAttribute('width', width)
  }

  /**
   * Sets the width attribute.
   *
   * @param width the width
   * @return this element for chaining
   */
  Symbol width(String width) {
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
   * @param height the height
   * @return this element for chaining
   */
  Symbol height(Number height) {
    addAttribute('height', height)
  }

  /**
   * Sets the height attribute.
   *
   * @param height the height
   * @return this element for chaining
   */
  Symbol height(String height) {
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

  /**
   * Sets the x attribute.
   *
   * @param x the x-coordinate
   * @return this element for chaining
   */
  Symbol x(Number x) {
    addAttribute('x', x)
  }

  /**
   * Sets the x attribute.
   *
   * @param x the x-coordinate
   * @return this element for chaining
   */
  Symbol x(String x) {
    addAttribute('x', x)
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
   * @param y the y-coordinate
   * @return this element for chaining
   */
  Symbol y(Number y) {
    addAttribute('y', y)
  }

  /**
   * Sets the y attribute.
   *
   * @param y the y-coordinate
   * @return this element for chaining
   */
  Symbol y(String y) {
    addAttribute('y', y)
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
   * Sets the preserve aspect ratio attribute.
   *
   * @param value the value
   * @return this element for chaining
   */
  Symbol preserveAspectRatio(String value) {
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

  /**
   * Sets the ref x attribute.
   *
   * @param refX the reference x-coordinate
   * @return this element for chaining
   */
  Symbol refX(Number refX) {
    addAttribute('refX', refX)
  }

  /**
   * Returns the ref x value.
   *
   * @return the ref x value
   */
  String getRefX() {
    getAttribute('refX')
  }

  /**
   * Sets the ref y attribute.
   *
   * @param refY the reference y-coordinate
   * @return this element for chaining
   */
  Symbol refY(Number refY) {
    addAttribute('refY', refY)
  }

  /**
   * Returns the ref y value.
   *
   * @return the ref y value
   */
  String getRefY() {
    getAttribute('refY')
  }

  /**
   * Sets the view box attribute.
   *
   * @param params the parameters
   * @return this element for chaining
   */
  Symbol viewBox(String params) {
    addAttribute('viewBox', params)
  }

  /**
   * Returns the view box value.
   *
   * @return the view box value
   */
  String getViewBox() {
    getAttribute('viewBox')
  }
}
