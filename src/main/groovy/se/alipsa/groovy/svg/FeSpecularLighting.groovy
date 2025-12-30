package se.alipsa.groovy.svg

/**
 * SVG {@code <feSpecularLighting>} filter primitive that computes specular lighting.
 */
class FeSpecularLighting extends FilterElement<FeSpecularLighting> implements LightSourceContainer<FeSpecularLighting> {

  static final String NAME='feSpecularLighting'

  /**
   * Creates a FeSpecularLighting.
   *
   * @param parent value
   */
  FeSpecularLighting(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   * Sets the surface scale attribute.
   *
   * @param value value
   * @return this element for chaining
   */
  FeSpecularLighting surfaceScale(Number value) {
    addAttribute('surfaceScale', value)
  }

  /**
   * Returns the surface scale value.
   *
   * @return the surface scale value
   */
  String getSurfaceScale() {
    getAttribute('surfaceScale')
  }

  /**
   * Sets the specular constant attribute.
   *
   * @param value value
   * @return this element for chaining
   */
  FeSpecularLighting specularConstant(Number value) {
    addAttribute('specularConstant', value)
  }

  /**
   * Returns the specular constant value.
   *
   * @return the specular constant value
   */
  String getSpecularConstant() {
    getAttribute('specularConstant')
  }

  /**
   * Sets the specular exponent attribute.
   *
   * @param value value
   * @return this element for chaining
   */
  FeSpecularLighting specularExponent(Number value) {
    addAttribute('specularExponent', value)
  }

  /**
   * Returns the specular exponent value.
   *
   * @return the specular exponent value
   */
  String getSpecularExponent() {
    getAttribute('specularExponent')
  }

}
