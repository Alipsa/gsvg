package se.alipsa.groovy.svg

class FeColorMatrix extends FilterElement<FeColorMatrix> {
  static final String NAME = 'feColorMatrix'

  FeColorMatrix(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
