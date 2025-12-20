package se.alipsa.groovy.svg

class Font extends AbstractElementContainer<Font> {

  static final String NAME = 'font'

  Font(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
