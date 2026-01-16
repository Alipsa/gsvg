package se.alipsa.groovy.svg



import groovy.transform.PackageScope
import org.dom4j.Element
import groovy.transform.CompileStatic

/**
 * SVG {@code <vkern>} element that defines vertical kerning adjustments.
 */
@CompileStatic
class Vkern extends SvgElement<Vkern> {

  static final String NAME = 'vkern'

  /**
   * Creates a Vkern.
   *
   * @param parent the parent SVG element
   */
  Vkern(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  @PackageScope
  Vkern(SvgElement parent, Element element) {
    super(parent, element)
  }

}