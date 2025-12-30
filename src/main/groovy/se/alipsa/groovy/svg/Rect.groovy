package se.alipsa.groovy.svg

import groovy.transform.PackageScope

/**
 * SVG {@code <rect>} element that draws a rectangle, optionally with rounded corners.
 */
class Rect extends AbstractShape<Rect>  {

  static final String NAME='rect'

  @PackageScope
  Rect(SvgElement parent) {
    super(parent, NAME)
  }

  @PackageScope
  Rect(SvgElement parent, Number width, Number height, boolean... addDefaults) {
    this(parent)
    addAttribute('width', width)
    addAttribute('height', height)
    if (addDefaults.length > 0 && addDefaults[0]) {
      x(0)
      y(0)
      rx(0)
      ry(0)
    }
  }

  /**
   * Sets the rectangle width in the current user coordinate system.
   *
   * @param width the width
   * @return this element for chaining
   */
  Rect width(Number width) {
    addAttribute('width', width)
  }

  /**
   * Sets the rectangle width, including unit values such as percentages.
   *
   * @param width the width
   * @return this element for chaining
   */
  Rect width(String width) {
    addAttribute('width', width)
  }

  /**
   * Returns the rectangle width.
   *
   * @return the width value
   */
  String getWidth() {
    getAttribute('width')
  }

  /**
   * Sets the rectangle height in the current user coordinate system.
   *
   * @param height the height
   * @return this element for chaining
   */
  Rect height(Number height) {
    addAttribute('height', height)
  }

  /**
   * Sets the rectangle height, including unit values such as percentages.
   *
   * @param height the height
   * @return this element for chaining
   */
  Rect height(String height) {
    addAttribute('height', height)
  }

  /**
   * Returns the rectangle height.
   *
   * @return the height value
   */
  String getHeight() {
    getAttribute('height')
  }

  /**
   * Sets the x-coordinate of the rectangle's left edge.
   *
   * @param x the x-coordinate
   * @return this element for chaining
   */
  Rect x(Number x) {
    addAttribute('x', x)
  }

  /**
   * Sets the x-coordinate of the rectangle's left edge, including unit values such as percentages.
   *
   * @param x the x-coordinate
   * @return this element for chaining
   */
  Rect x(String x) {
    addAttribute('x', x)
  }

  /**
   * Returns the x-coordinate of the rectangle's left edge.
   *
   * @return the x value
   */
  String getX() {
    getAttribute('x')
  }

  /**
   * Sets the y-coordinate of the rectangle's top edge.
   *
   * @param y the y-coordinate
   * @return this element for chaining
   */
  Rect y(Number y) {
    addAttribute('y', y)
  }

  /**
   * Sets the y-coordinate of the rectangle's top edge, including unit values such as percentages.
   *
   * @param y the y-coordinate
   * @return this element for chaining
   */
  Rect y(String y) {
    addAttribute('y', y)
  }

  /**
   * Returns the y-coordinate of the rectangle's top edge.
   *
   * @return the y value
   */
  String getY() {
    getAttribute('y')
  }

  /**
   * Sets the x-axis radius for rounded rectangle corners.
   *
   * @param rx the x-axis radius
   * @return this element for chaining
   */
  Rect rx(Number rx) {
    addAttribute('rx', rx)
  }

  /**
   * Returns the x-axis radius for rounded rectangle corners.
   *
   * @return the rx value
   */
  String getRx() {
    getAttribute('rx')
  }

  /**
   * Sets the y-axis radius for rounded rectangle corners.
   *
   * @param ry the y-axis radius
   * @return this element for chaining
   */
  Rect ry(Number ry) {
    addAttribute('ry', ry)
  }

  /**
   * Returns the y-axis radius for rounded rectangle corners.
   *
   * @return the ry value
   */
  String getRy() {
    getAttribute('ry')
  }

  /**
   * Sets the fill paint used to draw the rectangle interior.
   *
   * @param fill the fill color
   * @return this element for chaining
   */
  Rect fill(String fill) {
    addAttribute('fill', fill)
  }

  /**
   * Returns the fill paint used to draw the rectangle interior.
   *
   * @return the fill value
   */
  String getFill() {
    getAttribute('fill')
  }

  /**
   * Sets the stroke paint used to draw the rectangle outline.
   *
   * @param stroke the stroke color
   * @return this element for chaining
   */
  Rect stroke(String stroke) {
    addAttribute('stroke', stroke)
  }

  /**
   * Returns the stroke paint used to draw the rectangle outline.
   *
   * @return the stroke value
   */
  String getStroke() {
    getAttribute('stroke')
  }
}
