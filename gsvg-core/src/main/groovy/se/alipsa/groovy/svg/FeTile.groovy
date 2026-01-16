package se.alipsa.groovy.svg



import groovy.transform.PackageScope
import org.dom4j.Element
import groovy.transform.CompileStatic

/**
 * SVG {@code <feTile>} filter primitive that tiles the input image.
 */
@CompileStatic
class FeTile extends FilterElement<FeTile> {

  static final String NAME = 'feTile'

  /**
   * Creates a FeTile.
   *
   * @param parent the parent SVG element
   */
  FeTile(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  @PackageScope
  FeTile(SvgElement parent, Element element) {
    super(parent, element)
  }


  /**
   * Sets the in attribute.
   *
   * @param inStr the input source string identifier
   * @return this element for chaining
   */
  FeTile 'in'(String inStr) {
    addAttribute('in', inStr)
  }

  /**
   * Sets the in attribute.
   *
   * @param inEnum the input source enumeration
   * @return this element for chaining
   */
  FeTile 'in'(In inEnum) {
    addAttribute('in', inEnum.name())
  }

  /**
   * Returns the in value.
   *
   * @return the in value
   */
  String getIn() {
    getAttribute('in')
  }
}