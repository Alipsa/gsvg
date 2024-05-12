package se.alipsa.groovy.svg

class ClipPath extends AbstractElementContainer<ClipPath> {

  static final String NAME='clipPath'

  ClipPath(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
