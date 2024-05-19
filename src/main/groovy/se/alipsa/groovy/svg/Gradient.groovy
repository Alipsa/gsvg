package se.alipsa.groovy.svg

abstract class Gradient<T extends Gradient<T>> extends SvgElement<T> {

  List<Stop> stops = []

  Gradient(SvgElement<? extends SvgElement> parent, String name) {
    super(parent, name)
  }

  Stop addStop() {
    Stop stop = new Stop(this)
    stops << stop
    stop
  }
}
