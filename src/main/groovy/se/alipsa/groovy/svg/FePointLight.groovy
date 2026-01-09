package se.alipsa.groovy.svg



import groovy.transform.PackageScope
import org.dom4j.Element
import groovy.transform.CompileStatic

/**
 * SVG {@code <fePointLight>} element that defines a point light source.
 */
@CompileStatic
class FePointLight extends LightSourceElement<FePointLight> {

  static final String NAME = 'fePointLight'

  /**
   * Creates a FePointLight.
   *
   * @param parent the parent SVG element
   */
  FePointLight(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  @PackageScope
  FePointLight(SvgElement parent, Element element) {
    super(parent, element)
  }


  /**
   * Sets the z attribute.
   *
   * @param z the z-coordinate
   * @return this element for chaining
   */
  FePointLight z(Number z) {
    addAttribute('z', z)
  }

  /**
   * Sets the z attribute.
   *
   * @param z the z-coordinate
   * @return this element for chaining
   */
  FePointLight z(String z) {
    addAttribute('z', z)
  }

  /**
   * Returns the z value.
   *
   * @return the z value
   */
  String getZ() {
    getAttribute('z')
  }
}