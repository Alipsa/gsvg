package se.alipsa.groovy.svg

/**
 * SVG {@code <feFuncG>} element that defines the green channel transfer function.
 */
class FeFuncG extends FilterFunction<FeFuncG> {

  static final String NAME = 'feFuncG'

  /**
   * Creates a FeFuncG.
   *
   * @param parent value
   */
  FeFuncG(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
