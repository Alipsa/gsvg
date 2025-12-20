package se.alipsa.groovy.svg

class AltGlyph extends StringContentContainer<AltGlyph> {

  static final String NAME = 'altGlyph'

  AltGlyph(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
