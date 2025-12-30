package se.alipsa.groovy.svg

/**
 * SVG {@code <glyph>} element that defines a glyph in an SVG font.
 */
class Glyph extends SvgElement<Glyph> {

  static final String NAME = 'glyph'

  /**
   * Creates a Glyph.
   *
   * @param parent the parent SVG element
   */
  Glyph(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
