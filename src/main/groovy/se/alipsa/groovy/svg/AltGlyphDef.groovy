package se.alipsa.groovy.svg



import groovy.transform.PackageScope
import org.dom4j.Element
import groovy.transform.CompileStatic

/**
 * SVG {@code <altGlyphDef>} element that defines alternate glyph sets for text.
 */
@CompileStatic
class AltGlyphDef extends AbstractElementContainer<AltGlyphDef> {

  static final String NAME = 'altGlyphDef'

  /**
   * Creates a AltGlyphDef.
   *
   * @param parent the parent SVG element
   */
  AltGlyphDef(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  @PackageScope
  AltGlyphDef(SvgElement parent, Element element) {
    super(parent, element)
  }

}