package se.alipsa.groovy.svg

class Discard extends SvgElement<Discard> {

  static final String NAME = 'discard'

  Discard(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
