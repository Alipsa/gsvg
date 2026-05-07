package se.alipsa.groovy.svg

import groovy.transform.CompileStatic
import groovy.transform.PackageScope
import org.dom4j.Element

/**
 * SVG {@code <polygon>} element that draws a closed polygon from points.
 */
@CompileStatic
class Polygon extends AbstractPoly<Polygon> {

  static final String NAME='polygon'

  /**
   * Creates a Polygon.
   *
   * @param parent the parent SVG element
   */
  @PackageScope
  Polygon(SvgElement parent) {
    super(parent, NAME)
  }

  /**
   * Creates a Polygon with the given coordinate points.
   *
   * @param parent the parent SVG element
   * @param coordinates the points defining the polygon
   */
  @PackageScope
  Polygon(SvgElement parent, Coordinate... coordinates) {
    this(parent)
    if (coordinates.length > 0) {
      points(coordinates)
    }
  }

  /**
   * Creates a Polygon with the given coordinate pairs as lists of numbers.
   *
   * @param parent the parent SVG element
   * @param coordinates the points defining the polygon, each as a list of numbers
   */
  @PackageScope
  Polygon(SvgElement parent, List<Number>... coordinates) {
    this(parent)
    if (coordinates.length > 0) {
      points(coordinates)
    }
  }

  /**
   * Creates a Polygon by adopting an existing DOM4J Element.
   *
   * @param parent the parent SVG element
   * @param element the DOM4J element to adopt
   */
  @PackageScope
  Polygon(SvgElement parent, Element element) {
    super(parent, element)
  }

}
