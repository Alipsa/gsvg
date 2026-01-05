package se.alipsa.groovy.svg

import groovy.transform.CompileStatic

/**
 * SVG {@code <feFuncG>} element that defines the green channel transfer function.
 */
@CompileStatic
class FeFuncG extends FilterFunction<FeFuncG> {

  static final String NAME = 'feFuncG'

  /**
   * Creates a FeFuncG.
   *
   * @param parent the parent SVG element
   */
  FeFuncG(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
