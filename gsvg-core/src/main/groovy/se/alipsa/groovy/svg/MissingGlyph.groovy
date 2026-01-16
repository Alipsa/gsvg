package se.alipsa.groovy.svg



import groovy.transform.PackageScope
import org.dom4j.Element
import groovy.transform.CompileStatic

/**
 * SVG {@code <missing-glyph>} element that defines a fallback glyph for missing characters.
 */
@CompileStatic
class MissingGlyph extends SvgElement<MissingGlyph> {

  static final String NAME = 'missing-glyph'

  /**
   * Creates a MissingGlyph.
   *
   * @param parent the parent SVG element
   */
  MissingGlyph(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  @PackageScope
  MissingGlyph(SvgElement parent, Element element) {
    super(parent, element)
  }

}