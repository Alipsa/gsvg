package se.alipsa.groovy.svg



import groovy.transform.PackageScope
import org.dom4j.Element
import groovy.transform.CompileStatic

/**
 * SVG {@code <desc>} element that provides a human-readable description.
 */
@CompileStatic
class Desc extends StringContentContainer<Desc> {

  static final String NAME = 'desc'

  /**
   * Creates a Desc.
   *
   * @param parent the parent SVG element
   */
  Desc(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  @PackageScope
  Desc(SvgElement parent, Element element) {
    super(parent, element)
  }

}