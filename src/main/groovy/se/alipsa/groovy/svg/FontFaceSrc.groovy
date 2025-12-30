package se.alipsa.groovy.svg

/**
 * SVG {@code <font-face-src>} element that lists sources for a font face.
 */
class FontFaceSrc extends SvgElement<FontFaceSrc> {

  static final String NAME = 'font-face-src'

  /**
   * Creates a FontFaceSrc.
   *
   * @param parent value
   */
  FontFaceSrc(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
