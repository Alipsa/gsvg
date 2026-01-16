package se.alipsa.groovy.svg



import groovy.transform.PackageScope
import org.dom4j.Element
import groovy.transform.CompileStatic

/**
 * SVG {@code <altGlyph>} element that selects alternate glyphs for text rendering.
 */
@CompileStatic
class AltGlyph extends StringContentContainer<AltGlyph> {

  static final String NAME = 'altGlyph'

  /**
   * Creates a AltGlyph.
   *
   * @param parent the parent SVG element
   */
  AltGlyph(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  @PackageScope
  AltGlyph(SvgElement parent, Element element) {
    super(parent, element)
  }

}