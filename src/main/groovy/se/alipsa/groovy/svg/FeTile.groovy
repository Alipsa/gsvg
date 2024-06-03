package se.alipsa.groovy.svg

class FeTile extends FilterElement<FeTile> {

  static final String NAME = 'feTile'

  FeTile(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  FeTile in(String inStr) {
    addAttribute('in', inStr)
  }

  FeTile in(In inEnum) {
    addAttribute('in', inEnum.name())
  }

  String getIn() {
    getAttribute('in')
  }
}
