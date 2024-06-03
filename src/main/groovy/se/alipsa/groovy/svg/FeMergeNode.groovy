package se.alipsa.groovy.svg

class FeMergeNode extends FilterElement<FeMergeNode> {

  static final String NAME = 'feMergeNode'

  FeMergeNode(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  FeMergeNode in(String inStr) {
    addAttribute('in', inStr)
  }

  FeMergeNode in(In inEnum) {
    addAttribute('in', inEnum.name())
  }

  String getIn() {
    getAttribute('in')
  }
}
