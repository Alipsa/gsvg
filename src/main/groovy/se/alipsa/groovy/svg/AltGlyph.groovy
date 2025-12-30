package se.alipsa.groovy.svg

/**
 * SVG {@code <altGlyph>} element that selects alternate glyphs for text rendering.
 */
class AltGlyph extends StringContentContainer<AltGlyph> {

  static final String NAME = 'altGlyph'

  /**
   * Creates a AltGlyph.
   *
   * @param parent value
   */
  AltGlyph(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
