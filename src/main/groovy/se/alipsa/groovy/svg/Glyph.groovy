package se.alipsa.groovy.svg

import groovy.transform.CompileStatic

/**
 * SVG {@code <glyph>} element that defines a glyph in an SVG font.
 */
@CompileStatic
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
