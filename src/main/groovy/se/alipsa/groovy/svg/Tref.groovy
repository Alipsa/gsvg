package se.alipsa.groovy.svg

class Tref extends StringContentContainer<Tref> {

  static final String NAME = 'tref'

  Tref(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
