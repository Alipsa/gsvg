package se.alipsa.groovy.svg

/**
 * SVG {@code <font-face-uri>} element that references an external font resource.
 */
class FontFaceUri extends SvgElement<FontFaceUri> {

  static final String NAME = 'font-face-uri'

  /**
   * Creates a FontFaceUri.
   *
   * @param parent value
   */
  FontFaceUri(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
