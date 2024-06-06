package se.alipsa.groovy.svg


class Use extends SvgElement<Use> implements Animatable<Use> {
  static final String NAME = 'use'

  Use(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  Use clipPath(String path) {
    addAttribute('clip-path', path)
  }

  Use href(String ref) {
    element.addAttribute(xlink('href'), ref)
    this
  }

  Use fill(String fill) {
    addAttribute('fill', fill)
  }
}
