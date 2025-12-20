package se.alipsa.groovy.svg

class Cursor extends SvgElement<Cursor> {

  static final String NAME = 'cursor'

  Cursor(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
