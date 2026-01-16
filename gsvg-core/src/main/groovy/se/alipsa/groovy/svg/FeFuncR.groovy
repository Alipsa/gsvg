package se.alipsa.groovy.svg



import groovy.transform.PackageScope
import org.dom4j.Element
import groovy.transform.CompileStatic

/**
 * SVG {@code <feFuncR>} element that defines the red channel transfer function.
 */
@CompileStatic
class FeFuncR extends FilterFunction<FeFuncR> {

  static final String NAME = 'feFuncR'

  /**
   * Creates a FeFuncR.
   *
   * @param parent the parent SVG element
   */
  FeFuncR(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  @PackageScope
  FeFuncR(SvgElement parent, Element element) {
    super(parent, element)
  }

}