package se.alipsa.groovy.svg

import groovy.transform.CompileStatic
import groovy.transform.PackageScope
import org.dom4j.Element

/**
 * SVG {@code <ellipse>} element that draws an ellipse by center and radii.
 */
@CompileStatic
class Ellipse extends AbstractShape<Ellipse>  {

  static final String NAME='ellipse'

  /**
   * Creates an Ellipse.
   *
   * @param parent the parent SVG element
   */
  @PackageScope
  Ellipse(SvgElement parent) {
    super(parent,NAME)
  }

  /**
   * Creates an Ellipse with the given radii.
   *
   * @param parent the parent SVG element
   * @param rx the x-axis radius
   * @param ry the y-axis radius
   */
  @PackageScope
  Ellipse(SvgElement parent, Number rx, Number ry) {
    this(parent)
    addAttribute('rx', rx)
    addAttribute('ry', ry)
  }

  /**
   * Creates an Ellipse by adopting an existing DOM4J Element.
   *
   * @param parent the parent SVG element
   * @param element the DOM4J element to adopt
   */
  @PackageScope
  Ellipse(SvgElement parent, Element element) {
    super(parent, element)
  }

  /**
   * Sets the x-coordinate of the ellipse center (cx) in the current user coordinate system.
   *
   * @param cx the center x-coordinate
   * @return this element for chaining
   */
  Ellipse cx(Number cx) {
    addAttribute('cx', cx)
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
    addAttribute('cy', cy)
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
   * Sets the x-axis radius (rx) of the ellipse.
   *
   * @param rx the x-axis radius
   * @return this element for chaining
   */
  Ellipse rx(Number rx) {
    addAttribute('rx', rx)
  }

  /**
   * Sets the x-axis radius (rx) of the ellipse.
   *
   * @param rx the x-axis radius value
   * @return this element for chaining
   */
  Ellipse rx(String rx) {
    addAttribute('rx', "${rx}")
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
   * Sets the y-axis radius (ry) of the ellipse.
   *
   * @param ry the y-axis radius
   * @return this element for chaining
   */
  Ellipse ry(Number ry) {
    addAttribute('ry', ry)
  }

  /**
   * Sets the y-axis radius (ry) of the ellipse.
   *
   * @param ry the y-axis radius value
   * @return this element for chaining
   */
  Ellipse ry(String ry) {
    addAttribute('ry', "${ry}")
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
