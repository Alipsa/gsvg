package se.alipsa.groovy.svg

class HatchPath extends SvgElement<HatchPath> {

  static final String NAME = 'hatchpath'

  HatchPath(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
