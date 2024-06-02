package se.alipsa.groovy.svg

class FeSpotLight extends LightSourceElement<FeSpotLight> {

  static final String NAME='feSpotLight'

  FeSpotLight(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  FeSpotLight z(Number z) {
    addAttribute('z', z)
  }

  FeSpotLight z(String z) {
    addAttribute('z', z)
  }

  String getZ() {
    getAttribute('z')
  }

  FeSpotLight limitingConeAngle(Number angle) {
    addAttribute('limitingConeAngle', angle)
  }

  String getLimitingConeAngle() {
    getAttribute('limitingConeAngle')
  }

  FeSpotLight pointsAtX(Number value) {
    addAttribute('pointsAtX', value)
  }

  FeSpotLight pointsAtY(Number value) {
    addAttribute('pointsAtY', value)
  }

  String getPointsAtY() {
    getAttribute('pointsAtY')
  }

  FeSpotLight pointsAtZ(Number value) {
    addAttribute('pointsAtZ', value)
  }

  String getPointsAtZ() {
    getAttribute('pointsAtZ')
  }
}
