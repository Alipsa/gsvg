package se.alipsa.groovy.svg

/**
 * SVG {@code <missing-glyph>} element that defines a fallback glyph for missing characters.
 */
class MissingGlyph extends SvgElement<MissingGlyph> {

  static final String NAME = 'missing-glyph'

  /**
   * Creates a MissingGlyph.
   *
   * @param parent the parent SVG element
   */
  MissingGlyph(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
