package se.alipsa.groovy.svg

/**
 * SVG {@code <discard>} element that specifies when an element is discarded from rendering.
 */
class Discard extends SvgElement<Discard> {

  static final String NAME = 'discard'

  /**
   * Creates a Discard.
   *
   * @param parent the parent SVG element
   */
  Discard(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
