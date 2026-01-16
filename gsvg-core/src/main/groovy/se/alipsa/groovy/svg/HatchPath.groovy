package se.alipsa.groovy.svg



import groovy.transform.PackageScope
import org.dom4j.Element
import groovy.transform.CompileStatic

/**
 * SVG {@code <hatchpath>} element that defines a hatch path used by hatch.
 */
@CompileStatic
class HatchPath extends SvgElement<HatchPath> {

  static final String NAME = 'hatchpath'

  /**
   * Creates a HatchPath.
   *
   * @param parent the parent SVG element
   */
  HatchPath(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  @PackageScope
  HatchPath(SvgElement parent, Element element) {
    super(parent, element)
  }

}