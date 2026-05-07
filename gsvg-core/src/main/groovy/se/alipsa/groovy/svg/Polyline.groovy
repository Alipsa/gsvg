package se.alipsa.groovy.svg

import groovy.transform.CompileStatic
import groovy.transform.PackageScope
import org.dom4j.Element

/**
 * SVG {@code <polyline>} element that draws an open polyline from points.
 */
@CompileStatic
class Polyline extends AbstractPoly<Polyline> {

  static final String NAME='polyline'

  /**
   * Creates a Polyline.
   *
   * @param parent the parent SVG element
   */
  @PackageScope
  Polyline(SvgElement parent) {
    super(parent, NAME)
  }

  /**
   * Creates a Polyline with the given coordinate points.
   *
   * @param parent the parent SVG element
   * @param coordinates the points defining the polyline
   */
  @PackageScope
  Polyline(SvgElement parent, Coordinate... coordinates) {
    this(parent)
    if (coordinates.length > 0) {
      points(coordinates)
    }
  }

  /**
   * Creates a Polyline with the given coordinate pairs as lists of numbers.
   *
   * @param parent the parent SVG element
   * @param coordinates the points defining the polyline, each as a list of numbers
   */
  @PackageScope
  Polyline(SvgElement parent, List<Number>... coordinates) {
    this(parent)
    if (coordinates.length > 0) {
      points(coordinates)
    }
  }

  /**
   * Creates a Polyline by adopting an existing DOM4J Element.
   *
   * @param parent the parent SVG element
   * @param element the DOM4J element to adopt
   */
  @PackageScope
  Polyline(SvgElement parent, Element element) {
    super(parent, element)
  }

}
