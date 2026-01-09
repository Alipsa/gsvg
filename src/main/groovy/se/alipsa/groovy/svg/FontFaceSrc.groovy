package se.alipsa.groovy.svg



import groovy.transform.PackageScope
import org.dom4j.Element
import groovy.transform.CompileStatic

/**
 * SVG {@code <font-face-src>} element that lists sources for a font face.
 */
@CompileStatic
class FontFaceSrc extends SvgElement<FontFaceSrc> {

  static final String NAME = 'font-face-src'

  /**
   * Creates a FontFaceSrc.
   *
   * @param parent the parent SVG element
   */
  FontFaceSrc(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  @PackageScope
  FontFaceSrc(SvgElement parent, Element element) {
    super(parent, element)
  }

}