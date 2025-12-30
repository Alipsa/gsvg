package se.alipsa.groovy.svg

/**
 * SVG {@code <fePointLight>} element that defines a point light source.
 */
class FePointLight extends LightSourceElement<FePointLight> {

  static final String NAME = 'fePointLight'

  /**
   * Creates a FePointLight.
   *
   * @param parent value
   */
  FePointLight(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   * Sets the z attribute.
   *
   * @param z value
   * @return this element for chaining
   */
  FePointLight z(Number z) {
    addAttribute('z', z)
  }

  /**
   * Sets the z attribute.
   *
   * @param z value
   * @return this element for chaining
   */
  FePointLight z(String z) {
    addAttribute('z', z)
  }

  String getZ() {
    getAttribute('z')
  }
}
