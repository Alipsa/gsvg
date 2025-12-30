package se.alipsa.groovy.svg

import groovy.transform.PackageScope

/**
 * SVG {@code <ellipse>} element that draws an ellipse by center and radii.
 */
class Ellipse extends AbstractShape<Ellipse>  {

  static final String NAME='ellipse'

  @PackageScope
  Ellipse(SvgElement parent) {
    super(parent,NAME)
  }

  @PackageScope
  Ellipse(SvgElement parent, Number rx, Number ry) {
    this(parent)
    addAttribute('rx', "${rx}")
    addAttribute('ry', "${ry}")
  }

  /**
   * Sets the x-coordinate of the ellipse center (cx) in the current user coordinate system.
   *
   * @param cx the center x-coordinate
   * @return this element for chaining
   */
  Ellipse cx(Number cx) {
    addAttribute('cx', "${cx}")
  }

  /**
   * Sets the x-coordinate of the ellipse center (cx) in the current user coordinate system.
   *
   * @param cx value
   * @return this element for chaining
   */
  Ellipse cx(String cx) {
    addAttribute('cx', "${cx}")
  }
  /**
   * Returns the x-coordinate of the ellipse center (cx).
   *
   * @return the cx value
   */
  String getCx() {
    getAttribute('cx')
  }

  /**
   * Sets the y-coordinate of the ellipse center (cy) in the current user coordinate system.
   *
   * @param cy the center y-coordinate
   * @return this element for chaining
   */
  Ellipse cy(Number cy) {
    addAttribute('cy', "${cy}")
  }

  /**
   * Sets the y-coordinate of the ellipse center (cy) in the current user coordinate system.
   *
   * @param cy value
   * @return this element for chaining
   */
  Ellipse cy(String cy) {
    addAttribute('cy', "${cy}")
  }
  /**
   * Returns the y-coordinate of the ellipse center (cy).
   *
   * @return the cy value
   */
  String getCy() {
    getAttribute('cy')
  }

  /**
   * Sets the fill paint used to draw the ellipse interior.
   *
   * @param fill the fill color
   * @return this element for chaining
   */
  Ellipse fill(String fill) {
    addAttribute('fill', fill)
  }

  /**
   * Returns the fill paint used to draw the ellipse interior.
   *
   * @return the fill value
   */
  String getFill() {
    getAttribute('fill')
  }

  /**
   * Returns the x-axis radius (rx) of the ellipse.
   *
   * @return the rx value
   */
  String getRx() {
    getAttribute('rx')
  }

  /**
   * Returns the y-axis radius (ry) of the ellipse.
   *
   * @return the ry value
   */
  String getRy() {
    getAttribute('ry')
  }
}
