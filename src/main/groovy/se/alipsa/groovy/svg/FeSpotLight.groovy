package se.alipsa.groovy.svg

/**
 * SVG {@code <feSpotLight>} element that defines a spotlight source.
 */
class FeSpotLight extends LightSourceElement<FeSpotLight> {

  static final String NAME='feSpotLight'

  /**
   * Creates a FeSpotLight.
   *
   * @param parent the parent SVG element
   */
  FeSpotLight(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   * Sets the z attribute.
   *
   * @param z the z-coordinate
   * @return this element for chaining
   */
  FeSpotLight z(Number z) {
    addAttribute('z', z)
  }

  /**
   * Sets the z attribute.
   *
   * @param z the z-coordinate
   * @return this element for chaining
   */
  FeSpotLight z(String z) {
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

  /**
   * Sets the limiting cone angle attribute.
   *
   * @param angle the angle
   * @return this element for chaining
   */
  FeSpotLight limitingConeAngle(Number angle) {
    addAttribute('limitingConeAngle', angle)
  }

  /**
   * Returns the limiting cone angle value.
   *
   * @return the limiting cone angle value
   */
  String getLimitingConeAngle() {
    getAttribute('limitingConeAngle')
  }

  /**
   * Sets the points at x attribute.
   *
   * @param value the value
   * @return this element for chaining
   */
  FeSpotLight pointsAtX(Number value) {
    addAttribute('pointsAtX', value)
  }

  /**
   * Sets the points at y attribute.
   *
   * @param value the value
   * @return this element for chaining
   */
  FeSpotLight pointsAtY(Number value) {
    addAttribute('pointsAtY', value)
  }

  /**
   * Returns the points at y value.
   *
   * @return the points at y value
   */
  String getPointsAtY() {
    getAttribute('pointsAtY')
  }

  /**
   * Sets the points at z attribute.
   *
   * @param value the value
   * @return this element for chaining
   */
  FeSpotLight pointsAtZ(Number value) {
    addAttribute('pointsAtZ', value)
  }

  /**
   * Returns the points at z value.
   *
   * @return the points at z value
   */
  String getPointsAtZ() {
    getAttribute('pointsAtZ')
  }
}
