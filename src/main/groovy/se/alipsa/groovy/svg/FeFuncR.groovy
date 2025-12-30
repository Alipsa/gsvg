package se.alipsa.groovy.svg

/**
 * SVG {@code <feFuncR>} element that defines the red channel transfer function.
 */
class FeFuncR extends FilterFunction<FeFuncR> {

  static final String NAME = 'feFuncR'

  /**
   * Creates a FeFuncR.
   *
   * @param parent value
   */
  FeFuncR(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
