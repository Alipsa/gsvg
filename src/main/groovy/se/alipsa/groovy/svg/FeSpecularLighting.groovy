package se.alipsa.groovy.svg

class FeSpecularLighting extends FilterElement<FeSpecularLighting> implements LightSourceContainer<FeSpecularLighting> {

  static final String NAME='feSpecularLighting'

  LightSourceElement lightSource

  FeSpecularLighting(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  FeSpecularLighting surfaceScale(Number value) {
    addAttribute('surfaceScale', value)
  }

  String getSurfaceScale() {
    getAttribute('surfaceScale')
  }

  FeSpecularLighting specularConstant(Number value) {
    addAttribute('specularConstant', value)
  }

  String getSpecularConstant() {
    getAttribute('specularConstant')
  }

  FeSpecularLighting specularExponent(Number value) {
    addAttribute('specularExponent', value)
  }

  String getSpecularExponent() {
    getAttribute('specularExponent')
  }

}
