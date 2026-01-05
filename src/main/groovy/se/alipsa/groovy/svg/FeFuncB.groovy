package se.alipsa.groovy.svg

import groovy.transform.CompileStatic

/**
 * SVG {@code <feFuncB>} element that defines the blue channel transfer function.
 */
@CompileStatic
class FeFuncB extends FilterFunction<FeFuncB> {

  static final String NAME = 'feFuncB'

  /**
   * Creates a FeFuncB.
   *
   * @param parent the parent SVG element
   */
  FeFuncB(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
