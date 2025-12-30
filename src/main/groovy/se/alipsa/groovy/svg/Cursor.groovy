package se.alipsa.groovy.svg

/**
 * SVG {@code <cursor>} element that defines a custom cursor image and hotspot.
 */
class Cursor extends SvgElement<Cursor> {

  static final String NAME = 'cursor'

  /**
   * Creates a Cursor.
   *
   * @param parent the parent SVG element
   */
  Cursor(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
