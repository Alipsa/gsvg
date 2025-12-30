package se.alipsa.groovy.svg

/**
 * SVG {@code <font-face-name>} element that specifies a named font face source.
 */
class FontFaceName extends SvgElement<FontFaceName> {

  static final String NAME = 'font-face-name'

  /**
   * Creates a FontFaceName.
   *
   * @param parent value
   */
  FontFaceName(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
