package se.alipsa.groovy.svg

class MissingGlyph extends SvgElement<MissingGlyph> {

  static final String NAME = 'missing-glyph'

  MissingGlyph(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
