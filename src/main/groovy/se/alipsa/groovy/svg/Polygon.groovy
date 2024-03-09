package se.alipsa.groovy.svg

import groovy.transform.PackageScope

/**
 * <polygon points="${points.collect({it.toString()}).join(' ')}" ${optionalAttr('style', style)} />
 */
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
