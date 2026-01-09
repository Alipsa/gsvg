package se.alipsa.groovy.svg



import groovy.transform.PackageScope
import org.dom4j.Element
import groovy.transform.CompileStatic

/**
 * SVG {@code <feFuncA>} element that defines the alpha channel transfer function.
 */
@CompileStatic
class FeFuncA extends FilterFunction<FeFuncA> {

  static final String NAME = 'feFuncA'

  /**
   * Creates a FeFuncA.
   *
   * @param parent the parent SVG element
   */
  FeFuncA(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  @PackageScope
  FeFuncA(SvgElement parent, Element element) {
    super(parent, element)
  }

}