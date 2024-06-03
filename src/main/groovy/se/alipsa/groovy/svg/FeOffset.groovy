package se.alipsa.groovy.svg

class FeOffset extends FilterElement<FeOffset> {

  static final String NAME = 'feOffset'

  FeOffset(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /** The horizontal shift position for the offset */
  FeOffset dx(Number dx) {
    addAttribute('dx', "$dx")
  }

  String getDx() {
    getAttribute('dx')
  }

  /** The vertical shift position for the offset*/
  FeOffset dy(Number dy) {
    addAttribute('dy', "$dy")
  }

  String getDy() {
    getAttribute('dy')
  }
}
