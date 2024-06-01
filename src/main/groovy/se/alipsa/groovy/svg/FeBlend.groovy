package se.alipsa.groovy.svg

class FeBlend extends FilterElement<FeBlend> {

  static final String NAME = 'feBlend'


  enum Mode {
    normal, multiply, screen, darken, lighten;
  }

  FeBlend(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  FeBlend in(String inStr) {
    addAttribute('in', inStr)
  }

  FeBlend in(In inEnum) {
    addAttribute('in', inEnum.name())
  }

  String getIn() {
    getAttribute('in')
  }

  FeBlend in2(String inStr) {
    addAttribute('in2', inStr)
  }

  String getIn2() {
    getAttribute('in2')
  }

  FeBlend mode(String mode) {
    addAttribute('mode', mode)
  }

  FeBlend mode(Mode mode) {
    addAttribute('mode', mode.name())
  }

  String getMode() {
    getAttribute('mode')
  }
}
