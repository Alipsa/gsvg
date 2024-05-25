package se.alipsa.groovy.svg

class FeGaussianBlur extends SvgElement<FeGaussianBlur> {

  static final String NAME = 'feGaussianBlur'

  FeGaussianBlur(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  FeGaussianBlur in(String input) {
    addAttribute('in', input)
  }

  String getIn() {
    getAttribute('in')
  }

  FeGaussianBlur stdDeviation(Number deviation) {
    addAttribute('stdDeviation', deviation)
  }

  String getStdDeviation() {
    getAttribute('stdDeviation')
  }
}
