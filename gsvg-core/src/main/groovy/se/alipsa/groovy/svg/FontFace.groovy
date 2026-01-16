package se.alipsa.groovy.svg



import groovy.transform.PackageScope
import org.dom4j.Element
import groovy.transform.CompileStatic

/**
 * SVG {@code <font-face>} element that defines font metrics and properties.
 */
@CompileStatic
class FontFace extends AbstractElementContainer<FontFace> {

  static final String NAME = 'font-face'

  /**
   * Creates a FontFace.
   *
   * @param parent the parent SVG element
   */
  FontFace(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  @PackageScope
  FontFace(SvgElement parent, Element element) {
    super(parent, element)
  }

}