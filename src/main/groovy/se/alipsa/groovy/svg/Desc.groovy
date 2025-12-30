package se.alipsa.groovy.svg

/**
 * SVG {@code <desc>} element that provides a human-readable description.
 */
class Desc extends StringContentContainer<Desc> {

  static final String NAME = 'desc'

  /**
   * Creates a Desc.
   *
   * @param parent the parent SVG element
   */
  Desc(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
