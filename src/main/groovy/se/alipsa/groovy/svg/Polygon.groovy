package se.alipsa.groovy.svg

import groovy.transform.CompileStatic
import groovy.transform.PackageScope

/**
 * SVG {@code <polygon>} element that draws a closed polygon from points.
 */
@CompileStatic
class Polygon extends AbstractPoly<Polygon> {

  static final String NAME='polygon'

  @PackageScope
  Polygon(SvgElement parent) {
    super(parent, NAME)
  }

  @PackageScope
  Polygon(SvgElement parent, Coordinate... coordinates) {
    this(parent)
    if (coordinates.length > 0) points(coordinates)
  }

  @PackageScope
  Polygon(SvgElement parent, List<Number>... coordinates) {
    this(parent)
    if (coordinates.length > 0)  points(coordinates)
  }

}
