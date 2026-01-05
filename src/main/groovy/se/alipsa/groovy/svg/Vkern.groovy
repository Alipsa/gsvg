package se.alipsa.groovy.svg

import groovy.transform.CompileStatic

/**
 * SVG {@code <vkern>} element that defines vertical kerning adjustments.
 */
@CompileStatic
class Vkern extends SvgElement<Vkern> {

  static final String NAME = 'vkern'

  /**
   * Creates a Vkern.
   *
   * @param parent the parent SVG element
   */
  Vkern(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
