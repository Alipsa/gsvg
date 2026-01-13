package se.alipsa.groovy.svg

import groovy.transform.CompileStatic
import groovy.transform.PackageScope
import org.dom4j.Element

/**
 * SVG {@code <circle>} element that draws a circle by center and radius.
 */
@CompileStatic
class Circle extends AbstractShape<Circle> {

  static final String NAME='circle'

  @PackageScope
  Circle(SvgElement parent) {
    super(parent,NAME)
  }

  @PackageScope
  Circle(SvgElement parent, Element element) {
    super(parent, element)
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

}
