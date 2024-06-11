package se.alipsa.groovy.svg

class Desc extends StringContentContainer<Desc> {

  static final String NAME = 'desc'

  Desc(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
