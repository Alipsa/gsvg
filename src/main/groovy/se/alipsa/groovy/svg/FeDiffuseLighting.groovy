package se.alipsa.groovy.svg

class FeDiffuseLighting extends FilterElement {

  static final String NAME='feDiffuseLighting'

  LightSourceElement lightSource

  FeDiffuseLighting(SvgElement parent) {
    super(parent, NAME)
  }

  FeDiffuseLighting in(String inStr) {
    addAttribute('in', inStr)
  }

  FeDiffuseLighting in(In inEnum) {
    addAttribute('in', inEnum.name())
  }

  String getIn() {
    getAttribute('in')
  }

  FeDiffuseLighting lightingColor(String color) {
    addAttribute('lighting-color', color)
  }

  String getLightingColor() {
    getAttribute('lighting-color')
  }

  FeDistantLight addFeDistantLight() {
    lightSource = new FeDistantLight(this)
  }

  FePointLight addFePointLight() {
    lightSource = new FePointLight(this)
  }

  FeSpotLight addFeSpotLight() {
    lightSource = new FeSpotLight(this)
  }

}
