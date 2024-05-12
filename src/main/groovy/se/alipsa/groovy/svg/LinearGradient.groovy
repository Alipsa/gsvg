package se.alipsa.groovy.svg;

class LinearGradient extends SvgElement<LinearGradient>{

  static final String NAME = 'linearGradient'

  List<Stop> stops = []

  LinearGradient(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  Stop addStop() {
    Stop stop = new Stop(this)
    stops << stop
    stop
  }
}
