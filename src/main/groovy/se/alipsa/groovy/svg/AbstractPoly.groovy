package se.alipsa.groovy.svg

import org.dom4j.Attribute
import org.dom4j.Element

class AbstractPoly<T extends AbstractPoly<T>> extends AbstractShape<T> {

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

  T points(String points) {
    element.addAttribute('points', points)
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

  String getPoints() {
    getAttribute('points')
  }

  T fill(String value) {
    addAttribute('fill', value)
  }

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
