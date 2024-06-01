package se.alipsa.groovy.svg

class FeComposite extends FilterElement<FeComposite> {

  static final String NAME = 'feComposite'

  FeComposite(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  FeComposite operator(String value) {
    addAttribute('operator', value)
  }

  String getOperator() {
    getAttribute('operator')
  }

  FeComposite in(String inStr) {
    addAttribute('in', inStr)
  }

  FeComposite in(In inEnum) {
    addAttribute('in', inEnum.name())
  }

  String getIn() {
    getAttribute('in')
  }

  FeComposite in2(String inStr) {
    addAttribute('in2', inStr)
  }

  String getIn2() {
    getAttribute('in2')
  }
}
