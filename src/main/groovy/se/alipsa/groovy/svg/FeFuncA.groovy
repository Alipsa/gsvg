package se.alipsa.groovy.svg

/**
 * SVG {@code <feFuncA>} element that defines the alpha channel transfer function.
 */
class FeFuncA extends FilterFunction<FeFuncA> {

  static final String NAME = 'feFuncA'

  /**
   * Creates a FeFuncA.
   *
   * @param parent the parent SVG element
   */
  FeFuncA(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
