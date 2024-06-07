package se.alipsa.groovy.svg


class Use extends SvgElement<Use> implements Animatable<Use> {
  static final String NAME = 'use'

  Use(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  Use clipPath(String path) {
    addAttribute('clip-path', path)
  }

  Use xlinkHref(String ref) {
    element.addAttribute(xlink('href'), ref)
    this
  }

  @Override
  Use addAttribute(String name, Object value) {
    if ('href' == name) {
      xlinkHref("$value")
    } else {
      return super.addAttribute(name, value) as Use
    }
  }

  Use fill(String fill) {
    addAttribute('fill', fill)
  }

  Use x(Number x) {
    addAttribute('x', x)
  }

  Use x(String x) {
    addAttribute('x', x)
  }

  String getX() {
    getAttribute('x')
  }

  Use y(Number y) {
    addAttribute('y', y)
  }

  Use y(String y) {
    addAttribute('y', y)
  }

  String getY() {
    getAttribute('y')
  }
}
