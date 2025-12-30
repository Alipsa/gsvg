package se.alipsa.groovy.svg

/**
 * SVG {@code <feSpotLight>} element that defines a spotlight source.
 */
class FeSpotLight extends LightSourceElement<FeSpotLight> {

  static final String NAME='feSpotLight'

  /**
   * Creates a FeSpotLight.
   *
   * @param parent value
   */
  FeSpotLight(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   * Sets the z attribute.
   *
   * @param z value
   * @return this element for chaining
   */
  FeSpotLight z(Number z) {
    addAttribute('z', z)
  }

  /**
   * Sets the z attribute.
   *
   * @param z value
   * @return this element for chaining
   */
  FeSpotLight z(String z) {
    addAttribute('z', z)
  }

  String getZ() {
    getAttribute('z')
  }

  /**
   * Sets the limiting cone angle attribute.
   *
   * @param angle value
   * @return this element for chaining
   */
  FeSpotLight limitingConeAngle(Number angle) {
    addAttribute('limitingConeAngle', angle)
  }

  String getLimitingConeAngle() {
    getAttribute('limitingConeAngle')
  }

  /**
   * Sets the points at x attribute.
   *
   * @param value value
   * @return this element for chaining
   */
  FeSpotLight pointsAtX(Number value) {
    addAttribute('pointsAtX', value)
  }

  /**
   * Sets the points at y attribute.
   *
   * @param value value
   * @return this element for chaining
   */
  FeSpotLight pointsAtY(Number value) {
    addAttribute('pointsAtY', value)
  }

  String getPointsAtY() {
    getAttribute('pointsAtY')
  }

  /**
   * Sets the points at z attribute.
   *
   * @param value value
   * @return this element for chaining
   */
  FeSpotLight pointsAtZ(Number value) {
    addAttribute('pointsAtZ', value)
  }

  String getPointsAtZ() {
    getAttribute('pointsAtZ')
  }
}
