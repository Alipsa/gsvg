package se.alipsa.groovy.svg



import groovy.transform.PackageScope
import org.dom4j.Element
import groovy.transform.CompileStatic

/**
 * SVG {@code <feTurbulence>} filter primitive that generates noise or turbulence.
 */
@CompileStatic
class FeTurbulence extends FilterElement<FeTurbulence> {

  static final String NAME = 'feTurbulence'

  /**
   * Creates a FeTurbulence.
   *
   * @param parent the parent SVG element
   */
  FeTurbulence(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  @PackageScope
  FeTurbulence(SvgElement parent, Element element) {
    super(parent, element)
  }


  /**
   * Sets the type attribute.
   *
   * @param type the type
   * @return this element for chaining
   */
  FeTurbulence type(String type) {
    addAttribute('type', type)
  }

  /**
   * Returns the type value.
   *
   * @return the type value
   */
  String getType() {
    getAttribute('type')
  }

  /**
   * Sets the base frequency attribute.
   *
   * @param value the value
   * @return this element for chaining
   */
  FeTurbulence baseFrequency(Number value) {
    addAttribute('baseFrequency', value)
  }

  /**
   * Sets the base frequency attribute.
   *
   * @param value value
   * @return this element for chaining
   */
  FeTurbulence baseFrequency(String value) {
    addAttribute('baseFrequency', value)
  }
  /**
   * Returns the base frequency value.
   *
   * @return the base frequency value
   */
  String getBaseFrequency() {
    getAttribute('baseFrequency')
  }

  /**
   * Sets the num octaves attribute.
   *
   * @param value the value
   * @return this element for chaining
   */
  FeTurbulence numOctaves(Number value) {
    addAttribute('numOctaves', value)
  }

  /**
   * Sets the num octaves attribute.
   *
   * @param value value
   * @return this element for chaining
   */
  FeTurbulence numOctaves(String value) {
    addAttribute('numOctaves', value)
  }

}