package se.alipsa.groovy.svg

class Glyph extends SvgElement<Glyph> {

  static final String NAME = 'glyph'

  Glyph(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
