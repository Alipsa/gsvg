package se.alipsa.groovy.svg

import groovy.transform.CompileStatic

/**
 * SVG {@code <tref>} element that references another text element for reuse.
 */
@CompileStatic
class Tref extends StringContentContainer<Tref> {

  static final String NAME = 'tref'

  /**
   * Creates a Tref.
   *
   * @param parent the parent SVG element
   */
  Tref(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
