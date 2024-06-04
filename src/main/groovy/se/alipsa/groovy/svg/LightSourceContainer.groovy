package se.alipsa.groovy.svg

trait LightSourceContainer<T extends FilterElement<T>> {

  LightSourceElement lightSource

  FeDistantLight addFeDistantLight() {
    (lightSource = new FeDistantLight(this))
  }

  FePointLight addFePointLight() {
    (lightSource = new FePointLight(this))
  }

  FeSpotLight addFeSpotLight() {
    (lightSource = new FeSpotLight(this))
  }

  T in(String inStr) {
    addAttribute('in', inStr)
  }

  T in(In inEnum) {
    addAttribute('in', inEnum.name()) as T
  }

  String getIn() {
    getAttribute('in')
  }

  T lightingColor(String color) {
    addAttribute('lighting-color', color) as T
  }

  String getLightingColor() {
    getAttribute('lighting-color')
  }
}
