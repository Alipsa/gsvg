package se.alipsa.groovy.svg

import groovy.transform.PackageScope
import org.dom4j.Attribute
import org.dom4j.Element;

/**
 * <polygon points="${points.collect({it.toString()}).join(' ')}" ${optionalAttr('style', style)} />
 */
class Polygon extends AbstractPoly<Polygon> {

  @PackageScope
  Polygon(Element parent, Coordinate... coordinates) {
    super(parent.addElement('polygon'))
    if (coordinates.length > 0) points(coordinates)
  }

  @PackageScope
  Polygon(Element parent, List<Number>... coordinates) {
    super(parent.addElement('polygon'))
    if (coordinates.length > 0)  points(coordinates)
  }

}
