package se.alipsa.groovy.svg

/**
 * SVG {@code <solidcolor>} element that defines a solid color paint.
 */
class Solidcolor extends SvgElement<Solidcolor> {

  static final String NAME = 'solidcolor'

  /**
   * Creates a Solidcolor.
   *
   * @param parent the parent SVG element
   */
  Solidcolor(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
