package se.alipsa.groovy.svg

import groovy.transform.CompileStatic

/**
 * SVG {@code <hatch>} element that defines a hatch pattern paint.
 */
@CompileStatic
class Hatch extends AbstractElementContainer<Hatch> {

  static final String NAME = 'hatch'

  /**
   * Creates a Hatch.
   *
   * @param parent the parent SVG element
   */
  Hatch(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
