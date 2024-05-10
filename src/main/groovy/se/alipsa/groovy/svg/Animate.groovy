package se.alipsa.groovy.svg

class Animate extends SvgElement<Animate> {

  static final String NAME='animate'

  Animate(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
