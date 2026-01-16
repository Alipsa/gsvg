package se.alipsa.groovy.svg



import groovy.transform.PackageScope
import org.dom4j.Element
import groovy.transform.CompileStatic

/**
 * SVG {@code <glyphRef>} element that references a glyph for text rendering.
 */
@CompileStatic
class GlyphRef extends SvgElement<GlyphRef> {

  static final String NAME = 'glyphRef'

  /**
   * Creates a GlyphRef.
   *
   * @param parent the parent SVG element
   */
  GlyphRef(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  @PackageScope
  GlyphRef(SvgElement parent, Element element) {
    super(parent, element)
  }

}