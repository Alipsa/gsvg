package se.alipsa.groovy.svg



import groovy.transform.PackageScope
import org.dom4j.Element
import groovy.transform.CompileStatic

/**
 * SVG {@code <font>} element that defines an SVG font and its glyphs.
 */
@CompileStatic
class Font extends AbstractElementContainer<Font> {

  static final String NAME = 'font'

  /**
   * Creates a Font.
   *
   * @param parent the parent SVG element
   */
  Font(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  @PackageScope
  Font(SvgElement parent, Element element) {
    super(parent, element)
  }

}