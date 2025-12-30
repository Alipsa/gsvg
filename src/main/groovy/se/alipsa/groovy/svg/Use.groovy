package se.alipsa.groovy.svg


/**
 * SVG {@code <use>} element that reuses and renders a referenced element.
 */
class Use extends SvgElement<Use> implements Animatable<Use> {
  static final String NAME = 'use'

  /**
   * Creates a Use.
   *
   * @param parent value
   */
  Use(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   * Sets the clip path attribute.
   *
   * @param path value
   * @return this element for chaining
   */
  Use clipPath(String path) {
    addAttribute('clip-path', path)
  }

  /**
   * Sets the xlink href attribute.
   *
   * @param ref value
   * @return this element for chaining
   */
  Use xlinkHref(String ref) {
    element.addAttribute(xlink('href'), ref)
    this
  }

  /**
   * Sets the href attribute.
   *
   * @param ref value
   * @return this element for chaining
   */
  Use href(String ref) {
    element.addAttribute('href', "$ref")
    this
  }

  String getHref() {
    getAttribute('href') ?: getAttribute(xlink('href'))
  }

  String getXlinkHref() {
    getAttribute(xlink('href'))
  }

  /**
   * Adds an attribute to this element.
   *
   * @param name value
   * @param value value
   * @return the created element
   */
  @Override
  Use addAttribute(String name, Object value) {
    if ('href' == name) {
      element.addAttribute('href', "$value")
      return this
    }
    if ('xlink:href' == name) {
      element.addAttribute(xlink('href'), "$value")
      return this
    }
    return super.addAttribute(name, value) as Use
  }

  /**
   * Sets the fill attribute.
   *
   * @param fill value
   * @return this element for chaining
   */
  Use fill(String fill) {
    addAttribute('fill', fill)
  }

  /**
   * Sets the x attribute.
   *
   * @param x value
   * @return this element for chaining
   */
  Use x(Number x) {
    addAttribute('x', x)
  }

  /**
   * Sets the x attribute.
   *
   * @param x value
   * @return this element for chaining
   */
  Use x(String x) {
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
  Use y(Number y) {
    addAttribute('y', y)
  }

  /**
   * Sets the y attribute.
   *
   * @param y value
   * @return this element for chaining
   */
  Use y(String y) {
    addAttribute('y', y)
  }

  String getY() {
    getAttribute('y')
  }
}
