package se.alipsa.groovy.svg

import org.dom4j.Attribute
import org.dom4j.Element

class AbstractPoly<T extends AbstractPoly<T>> extends SvgElement<T> {

  AbstractPoly(SvgElement element, String name) {
    super(element, name)
  }

  T points(Coordinate... points) {
    element.addAttribute('points', toAttributeValues(points))
    this as T
  }

  T points(List<Number>... points) {
    element.addAttribute('points', toAttributeValues(points))
    this as T
  }

  T addPoints(Coordinate... points) {
    Attribute pa = element.attribute(points)
    String paVal = pa == null ? toAttributeValues(points) : pa.getValue() + ' ' + toAttributeValues(points)
    pa.setValue(paVal)
    this as T
  }

  T addPoint(Coordinate point) {
    Attribute pa = element.attribute(points)
    String paVal = pa == null ? toAttributeValues(points) : pa.getValue() + ' ' + point.toString()
    pa.setValue(paVal)
    this as T
  }

  T style(String style) {
    element.addAttribute('style', style)
    this as T
  }

  private static String toAttributeValues(Coordinate... points) {
    return (points as List<Coordinate>).collect({ it.toString() }).join(' ')
  }

  private static String toAttributeValues(List<Number>... points) {
    return points.collect({ (it as Coordinate).toString() }).join(' ')
  }
}
