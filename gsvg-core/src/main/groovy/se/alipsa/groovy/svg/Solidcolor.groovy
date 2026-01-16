package se.alipsa.groovy.svg



import groovy.transform.PackageScope
import org.dom4j.Element
import groovy.transform.CompileStatic

/**
 * SVG {@code <solidcolor>} element that defines a solid color paint.
 */
@CompileStatic
class Solidcolor extends SvgElement<Solidcolor> {

  static final String NAME = 'solidcolor'

  /**
   * Creates a Solidcolor.
   *
   * @param parent the parent SVG element
   */
  Solidcolor(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  @PackageScope
  Solidcolor(SvgElement parent, Element element) {
    super(parent, element)
  }

}