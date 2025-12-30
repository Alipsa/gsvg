package se.alipsa.groovy.svg

/**
 * SVG {@code <altGlyphDef>} element that defines alternate glyph sets for text.
 */
class AltGlyphDef extends AbstractElementContainer<AltGlyphDef> {

  static final String NAME = 'altGlyphDef'

  /**
   * Creates a AltGlyphDef.
   *
   * @param parent the parent SVG element
   */
  AltGlyphDef(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
