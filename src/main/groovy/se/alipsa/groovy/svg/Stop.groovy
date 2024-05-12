package se.alipsa.groovy.svg

class Stop extends SvgElement<Stop> {

  static final String NAME = 'stop'

  Stop(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
