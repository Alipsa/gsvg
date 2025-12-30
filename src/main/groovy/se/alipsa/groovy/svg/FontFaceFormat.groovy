package se.alipsa.groovy.svg

/**
 * SVG {@code <font-face-format>} element that specifies the format of an external font source.
 */
class FontFaceFormat extends SvgElement<FontFaceFormat> {

  static final String NAME = 'font-face-format'

  /**
   * Creates a FontFaceFormat.
   *
   * @param parent value
   */
  FontFaceFormat(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
