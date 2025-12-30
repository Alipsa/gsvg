package se.alipsa.groovy.svg

/**
 * SVG {@code <feDistantLight>} element that defines a distant light source.
 */
class FeDistantLight extends LightSourceElement<FeDistantLight> {

  static final String NAME = 'feDistantLight'

  /**
   * Creates a FeDistantLight.
   *
   * @param parent value
   */
  FeDistantLight(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   * Sets the azimuth attribute.
   *
   * @param value value
   * @return this element for chaining
   */
  FeDistantLight azimuth(Number value) {
    addAttribute('azimuth', value)
  }

  /**
   * Returns the azimuth value.
   *
   * @return the azimuth value
   */
  String getAzimuth() {
    getAttribute('azimuth')
  }

  /**
   * Sets the elevation attribute.
   *
   * @param value value
   * @return this element for chaining
   */
  FeDistantLight elevation(Number value) {
    addAttribute('elevation', value)
  }

  /**
   * Returns the elevation value.
   *
   * @return the elevation value
   */
  String getElevation() {
    getAttribute('elevation')
  }
}
