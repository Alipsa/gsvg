package se.alipsa.groovy.svg



import groovy.transform.PackageScope
import org.dom4j.Element
import groovy.transform.CompileStatic

/**
 * SVG {@code <font-face-format>} element that specifies the format of an external font source.
 */
@CompileStatic
class FontFaceFormat extends SvgElement<FontFaceFormat> {

  static final String NAME = 'font-face-format'

  /**
   * Creates a FontFaceFormat.
   *
   * @param parent the parent SVG element
   */
  FontFaceFormat(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  @PackageScope
  FontFaceFormat(SvgElement parent, Element element) {
    super(parent, element)
  }

}