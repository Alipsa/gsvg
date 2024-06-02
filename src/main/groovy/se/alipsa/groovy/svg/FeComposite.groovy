package se.alipsa.groovy.svg

class FeComposite extends FilterElement<FeComposite> {

  static final String NAME = 'feComposite'

  FeComposite(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   *
   * @param value, one of over | in | out | atop | xor | lighter | arithmetic
   * @return
   */
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

  /** value for the arithmetic operator */
  FeComposite k1(Number value) {
    addAttribute('k1', value)
  }

  String getK1() {
    getAttribute('k1')
  }

  /** value for the arithmetic operator */
  FeComposite k2(Number value) {
    addAttribute('k2', value)
  }

  String getK2() {
    getAttribute('k2')
  }

  /** value for the arithmetic operator */
  FeComposite k3(Number value) {
    addAttribute('k3', value)
  }

  String getK3() {
    getAttribute('k3')
  }

  /** value for the arithmetic operator */
  FeComposite k4(Number value) {
    addAttribute('k4', value)
  }

  String getK4() {
    getAttribute('k4')
  }
}
