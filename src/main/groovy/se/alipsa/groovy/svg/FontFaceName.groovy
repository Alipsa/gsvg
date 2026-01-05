package se.alipsa.groovy.svg

import groovy.transform.CompileStatic

/**
 * SVG {@code <font-face-name>} element that specifies a named font face source.
 */
@CompileStatic
class FontFaceName extends SvgElement<FontFaceName> {

  static final String NAME = 'font-face-name'

  /**
   * Creates a FontFaceName.
   *
   * @param parent the parent SVG element
   */
  FontFaceName(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
