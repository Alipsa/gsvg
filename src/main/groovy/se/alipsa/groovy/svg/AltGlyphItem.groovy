package se.alipsa.groovy.svg

/**
 * SVG {@code <altGlyphItem>} element that groups alternate glyph choices.
 */
class AltGlyphItem extends SvgElement<AltGlyphItem> {

  static final String NAME = 'altGlyphItem'

  /**
   * Creates a AltGlyphItem.
   *
   * @param parent value
   */
  AltGlyphItem(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
