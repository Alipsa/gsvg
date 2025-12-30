package se.alipsa.groovy.svg

/**
 * SVG {@code <font>} element that defines an SVG font and its glyphs.
 */
class Font extends AbstractElementContainer<Font> {

  static final String NAME = 'font'

  /**
   * Creates a Font.
   *
   * @param parent value
   */
  Font(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
