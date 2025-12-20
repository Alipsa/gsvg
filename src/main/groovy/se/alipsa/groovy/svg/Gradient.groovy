package se.alipsa.groovy.svg

abstract class Gradient<T extends Gradient<T>> extends SvgElement<T> {

  Gradient(SvgElement<? extends SvgElement> parent, String name) {
    super(parent, name)
  }

  Stop addStop() {
    add(new Stop(this))
  }

  T gradientUnits(String units) {
    addAttribute('gradientUnits', units)
    this as T
  }

  T gradientTransform(String transform) {
    addAttribute('gradientTransform', transform)
    this as T
  }

  T spreadMethod(String method) {
    addAttribute('spreadMethod', method)
    this as T
  }

  List<Stop> getStops() {
    children.findAll {it instanceof Stop} as List<Stop>
  }
}
