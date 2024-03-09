package se.alipsa.groovy.svg

import groovy.transform.PackageScope
import org.dom4j.Element;

/**
 * <polyline points="${points.collect({it.toString()}).join(' ')}" ${optionalAttr('style', style)} />
 */
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
