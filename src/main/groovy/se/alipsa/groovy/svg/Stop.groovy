package se.alipsa.groovy.svg

class Stop extends SvgElement<Stop> {

  static final String NAME = 'stop'

  Stop(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  Stop offset(String offset) {
    addAttribute("offset", offset)
  }

  String getOffset() {
    getAttribute("offset")
  }

  Stop stopColor(String color) {
    addAttribute("stop-color", color)
  }

  String getStopColor() {
    getAttribute("stop-color")
  }
}
