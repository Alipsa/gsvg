package se.alipsa.groovy.svg

class FePointLight extends LightSourceElement<FePointLight> {

  static final String NAME = 'fePointLight'

  FePointLight(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  FePointLight z(Number z) {
    addAttribute('z', z)
  }

  FePointLight z(String z) {
    addAttribute('z', z)
  }

  String getZ() {
    getAttribute('z')
  }
}
