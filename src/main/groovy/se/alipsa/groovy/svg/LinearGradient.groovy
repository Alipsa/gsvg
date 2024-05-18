package se.alipsa.groovy.svg;

/**
 * Parents: a | defs | g | marker | mask | pattern | svg | symbol.
 */
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

  LinearGradient x1(String x1) {
    addAttribute('x1', x1)
  }

  String getX1() {
    getAttribute('x1')
  }

  LinearGradient x2(String x2) {
    addAttribute('x2', x2)
  }

  String getX2() {
    getAttribute('x2')
  }

  LinearGradient y1(String y1) {
    addAttribute('y1', y1)
  }

  String getY1() {
    getAttribute('y1')
  }

  LinearGradient y2(String y2) {
    addAttribute('y2', y2)
  }

  String getY2() {
    getAttribute('y2')
  }
}
