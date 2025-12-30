package se.alipsa.groovy.svg

/**
 * SVG {@code <font-face>} element that defines font metrics and properties.
 */
class FontFace extends AbstractElementContainer<FontFace> {

  static final String NAME = 'font-face'

  /**
   * Creates a FontFace.
   *
   * @param parent the parent SVG element
   */
  FontFace(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
