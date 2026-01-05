package se.alipsa.groovy.svg

import groovy.transform.CompileStatic
import groovy.transform.PackageScope

/**
 * SVG {@code <polyline>} element that draws an open polyline from points.
 */
@CompileStatic
class Polyline extends AbstractPoly<Polyline> {

  static final String NAME='polyline'

  @PackageScope
  Polyline(SvgElement parent) {
    super(parent, NAME)
  }

  @PackageScope
  Polyline(SvgElement parent, Coordinate... coordinates) {
    this(parent)
    if (coordinates.length > 0) points(coordinates)
  }

  @PackageScope
  Polyline(SvgElement parent, List<Number>... coordinates) {
    this(parent)
    if (coordinates.length > 0) points(coordinates)
  }

}
