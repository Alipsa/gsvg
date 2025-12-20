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
  }

  T gradientTransform(String transform) {
    addAttribute('gradientTransform', transform)
  }

  T spreadMethod(String method) {
    addAttribute('spreadMethod', method)
  }

  List<Stop> getStops() {
    children.findAll {it instanceof Stop} as List<Stop>
  }
}
