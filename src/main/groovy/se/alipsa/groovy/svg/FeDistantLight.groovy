package se.alipsa.groovy.svg

class FeDistantLight extends LightSourceElement<FeDistantLight> {

  static final String NAME = 'feDistantLight'

  FeDistantLight(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  FeDistantLight azimuth(Number value) {
    addAttribute('azimuth', value)
  }

  String getAzimuth() {
    getAttribute('azimuth')
  }

  FeDistantLight elevation(Number value) {
    addAttribute('elevation', value)
  }

  String getElevation() {
    getAttribute('elevation')
  }
}
