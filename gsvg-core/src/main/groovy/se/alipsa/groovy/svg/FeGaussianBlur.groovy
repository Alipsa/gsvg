package se.alipsa.groovy.svg



import groovy.transform.PackageScope
import org.dom4j.Element
import groovy.transform.CompileStatic

/**
 * SVG {@code <feGaussianBlur>} filter primitive that applies a Gaussian blur.
 */
@CompileStatic
class FeGaussianBlur extends FilterElement<FeGaussianBlur> {

  static final String NAME = 'feGaussianBlur'

  /**
   * Creates a FeGaussianBlur.
   *
   * @param parent the parent SVG element
   */
  FeGaussianBlur(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  @PackageScope
  FeGaussianBlur(SvgElement parent, Element element) {
    super(parent, element)
  }


  /**
   * Sets the in attribute.
   *
   * @param input the input
   * @return this element for chaining
   */
  FeGaussianBlur 'in'(String input) {
    addAttribute('in', input)
  }

  /**
   * Returns the in value.
   *
   * @return the in value
   */
  String getIn() {
    getAttribute('in')
  }

  /**
   * Sets the std deviation attribute.
   *
   * @param deviation the standard deviation for blur
   * @return this element for chaining
   */
  FeGaussianBlur stdDeviation(Number deviation) {
    addAttribute('stdDeviation', deviation)
  }

  /**
   * Sets the std deviation attribute.
   *
   * @param deviation value
   * @return this element for chaining
   */
  FeGaussianBlur stdDeviation(String deviation) {
    addAttribute('stdDeviation', deviation)
  }
  /**
   * Returns the std deviation value.
   *
   * @return the std deviation value
   */
  String getStdDeviation() {
    getAttribute('stdDeviation')
  }
}