package se.alipsa.groovy.svg

import groovy.transform.CompileStatic

/**
 * SVG {@code <font-face-uri>} element that references an external font resource.
 */
@CompileStatic
class FontFaceUri extends SvgElement<FontFaceUri> {

  static final String NAME = 'font-face-uri'

  /**
   * Creates a FontFaceUri.
   *
   * @param parent the parent SVG element
   */
  FontFaceUri(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
