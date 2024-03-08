package se.alipsa.groovy.svg

import groovy.transform.PackageScope
import org.dom4j.Element;

/**
 * <polyline points="${points.collect({it.toString()}).join(' ')}" ${optionalAttr('style', style)} />
 */
class Polyline extends AbstractPoly<Polyline> {

  @PackageScope
  Polyline(SvgElement parent, Coordinate... coordinates) {
    super(parent.element.addElement('polyline'))
    if (coordinates.length > 0) points(coordinates)
  }

  @PackageScope
  Polyline(SvgElement parent, List<Number>... coordinates) {
    super(parent.element.addElement('polyline'))
    if (coordinates.length > 0) points(coordinates)
  }

}
