package se.alipsa.groovy.svg

class Filter extends AbstractElementContainer<Filter> {

  static final String NAME = 'filter'

  Filter(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  Filter x(Number x) {
    addAttribute('x', "$x")
  }

  String getX() {
    getAttribute('x')
  }

  Filter y(Number y) {
    addAttribute('y', "$y")
  }

  String getY() {
    getAttribute('y')
  }

  FeGaussianBlur addFeGaussianBlur() {
    add(new FeGaussianBlur(this))
  }
}
