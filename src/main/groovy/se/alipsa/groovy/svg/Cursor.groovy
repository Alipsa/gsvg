package se.alipsa.groovy.svg



import groovy.transform.PackageScope
import org.dom4j.Element
import groovy.transform.CompileStatic

/**
 * SVG {@code <cursor>} element that defines a custom cursor image and hotspot.
 */
@CompileStatic
class Cursor extends SvgElement<Cursor> {

  static final String NAME = 'cursor'

  /**
   * Creates a Cursor.
   *
   * @param parent the parent SVG element
   */
  Cursor(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  @PackageScope
  Cursor(SvgElement parent, Element element) {
    super(parent, element)
  }

}