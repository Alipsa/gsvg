package se.alipsa.groovy.svg

import groovy.transform.CompileStatic
import org.dom4j.Attribute
import org.dom4j.Element

/**
 * Base class for polygon and polyline elements with points list handling.
 */
@CompileStatic
class AbstractPoly<T extends AbstractPoly<T>> extends AbstractShape<T> {

  /**
   * Creates a AbstractPoly.
   *
   * @param element the element
   * @param name the name of the element
   */
  AbstractPoly(SvgElement element, String name) {
    super(element, name)
  }

  /**
   * Creates a AbstractPoly by adopting an existing DOM4J Element.
   * Used for cloning/copying operations.
   *
   * @param parent the parent SVG element
   * @param element the DOM4J element to adopt
   */
  AbstractPoly(SvgElement parent, Element element) {
    super(parent, element)
  }

  /**
   * Sets the points attribute as a list of x,y coordinate pairs.
   *
   * @param points the points defining the shape
   * @return this element for chaining
   */
  T points(Coordinate... points) {
    element.addAttribute('points', toAttributeValues(points))
    this as T
  }

  /**
   * Sets the points attribute from numeric x,y pairs.
   *
   * @param points the points defining the shape
   * @return this element for chaining
   */
  T points(List<Number>... points) {
    element.addAttribute('points', toAttributeValues(points))
    this as T
  }

  /**
   * Sets the raw points attribute string (for example `"0,0 10,0 10,10"`).
   *
   * @param points the points defining the shape
   * @return this element for chaining
   */
  T points(String points) {
    element.addAttribute('points', points)
    this as T
  }

  /**
   * Appends additional points to the existing points list.
   *
   * @param points the points defining the shape
   * @return this element for chaining
   */
  T addPoints(Coordinate... points) {
    Attribute pa = element.attribute('points')
    if (pa == null) {
      addAttribute('points', toAttributeValues(points))
    } else {
      pa.setValue(pa.getValue() + ' ' + toAttributeValues(points))
    }
    this as T
  }

  /**
   * Appends a single point to the existing points list.
   *
   * @param point the point
   * @return this element for chaining
   */
  T addPoint(Coordinate point) {
    Attribute pa = element.attribute('points')
    if (pa == null) {
      addAttribute('points', point.toString())
    } else {
      pa.setValue(pa.getValue() + ' ' + point.toString())
    }
    this as T
  }

  /**
   * Returns the points attribute string.
   *
   * @return the points value
   */
  String getPoints() {
    getAttribute('points')
  }

  /**
   * Sets the fill paint used to draw the polygon or polyline interior.
   *
   * @param value the value
   * @return this element for chaining
   */
  T fill(String value) {
    addAttribute('fill', value)
  }

  /**
   * Returns the fill paint used to draw the polygon or polyline interior.
   *
   * @return the fill value
   */
  String getFill() {
    getAttribute('fill')
  }

  private static String toAttributeValues(Coordinate... points) {
    return (points as List<Coordinate>).collect({ it.toString() }).join(' ')
  }

  private static String toAttributeValues(List<Number>... points) {
    return points.collect({ (it as Coordinate).toString() }).join(' ')
  }
}
