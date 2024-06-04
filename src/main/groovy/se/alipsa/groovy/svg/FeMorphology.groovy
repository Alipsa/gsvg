package se.alipsa.groovy.svg

class FeMorphology extends FilterElement<FeMorphology> {

  static final String NAME='feMorphology'

  enum Operator {
    erode, dilate
  }

  FeMorphology(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  FeMorphology operator(String mode) {
    addAttribute('operator', mode)
  }

  FeMorphology operator(Operator mode) {
    addAttribute('operator', mode.name())
  }

  String getOperator() {
    getAttribute('operator')
  }

  FeMorphology in(String inStr) {
    addAttribute('in', inStr)
  }

  FeMorphology in(In inEnum) {
    addAttribute('in', inEnum.name())
  }

  String getIn() {
    getAttribute('in')
  }

  FeMorphology radius(Number value) {
    addAttribute('radius', value)
  }

  FeMorphology radius(String value) {
    addAttribute('radius', value)
  }

  String getRadius() {
    getAttribute('radius')
  }
}
