package se.alipsa.groovy.svg

import groovy.transform.PackageScope

/**
 * SVG {@code <circle>} element that draws a circle by center and radius.
 */
class Circle extends AbstractShape<Circle> {

  static final String NAME='circle'

  @PackageScope
  Circle(SvgElement parent) {
    super(parent,NAME)
  }

  /**
   * Sets the x-coordinate of the circle center (cx) in the current user coordinate system.
   *
   * @param cx the center x-coordinate
   * @return this element for chaining
   */
  Circle cx(Number cx) {
    addAttribute('cx', cx)
  }

  /**
   * Sets the x-coordinate of the circle center (cx), including unit values such as percentages.
   *
   * @param cx the center x-coordinate
   * @return this element for chaining
   */
  Circle cx(String cx) {
    addAttribute('cx', cx)
  }

  /**
   * Returns the x-coordinate of the circle center (cx).
   *
   * @return the cx value
   */
  String getCx() {
    getAttribute('cx')
  }

  /**
   * Sets the y-coordinate of the circle center (cy) in the current user coordinate system.
   *
   * @param cy the center y-coordinate
   * @return this element for chaining
   */
  Circle cy(Number cy) {
    addAttribute('cy', cy)
  }

  /**
   * Sets the y-coordinate of the circle center (cy), including unit values such as percentages.
   *
   * @param cy the center y-coordinate
   * @return this element for chaining
   */
  Circle cy(String cy) {
    addAttribute('cy', cy)
  }

  /**
   * Returns the y-coordinate of the circle center (cy).
   *
   * @return the cy value
   */
  String getCy() {
    getAttribute('cy')
  }

  /**
   * Sets the circle radius (r) in the current user coordinate system.
   *
   * @param r the radius
   * @return this element for chaining
   */
  Circle r(Number r) {
    addAttribute('r', "$r")
  }

  /**
   * Sets the circle radius (r) in the current user coordinate system.
   *
   * @param r value
   * @return this element for chaining
   */
  Circle r(String r) {
    addAttribute('r', "$r")
  }
  /**
   * Returns the circle radius (r).
   *
   * @return the r value
   */
  String getR() {
    getAttribute('r')
  }

  /**
   * Sets the stroke paint used to draw the circle outline.
   *
   * @param stroke the stroke color
   * @return this element for chaining
   */
  Circle stroke(String stroke) {
    addAttribute('stroke', "$stroke")
  }

  /**
   * Returns the stroke paint used to draw the circle outline.
   *
   * @return the stroke value
   */
  String getStroke() {
    getAttribute('stroke')
  }

  /**
   * Sets the stroke width used to draw the circle outline.
   *
   * @param strokeWidth the stroke width
   * @return this element for chaining
   */
  Circle strokeWidth(Number strokeWidth) {
    addAttribute('stroke-width', "$strokeWidth")
  }

  /**
   * Sets the stroke width used to draw the circle outline.
   *
   * @param strokeWidth value
   * @return this element for chaining
   */
  Circle strokeWidth(String strokeWidth) {
    addAttribute('stroke-width', "$strokeWidth")
  }
  /**
   * Returns the stroke width used to draw the circle outline.
   *
   * @return the stroke width value
   */
  String getStrokeWidth() {
    getAttribute('stroke-width')
  }

  /**
   * Sets the fill paint used to draw the circle interior.
   *
   * @param fill the fill color
   * @return this element for chaining
   */
  Circle fill(String fill) {
    addAttribute('fill', "$fill")
  }

  /**
   * Returns the fill paint used to draw the circle interior.
   *
   * @return the fill value
   */
  String getFill() {
    getAttribute('fill')
  }
}
