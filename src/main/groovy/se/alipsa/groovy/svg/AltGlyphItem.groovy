package se.alipsa.groovy.svg



import groovy.transform.PackageScope
import org.dom4j.Element
import groovy.transform.CompileStatic

/**
 * SVG {@code <altGlyphItem>} element that groups alternate glyph choices.
 */
@CompileStatic
class AltGlyphItem extends SvgElement<AltGlyphItem> {

  static final String NAME = 'altGlyphItem'

  /**
   * Creates a AltGlyphItem.
   *
   * @param parent the parent SVG element
   */
  AltGlyphItem(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  @PackageScope
  AltGlyphItem(SvgElement parent, Element element) {
    super(parent, element)
  }

}