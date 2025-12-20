package se.alipsa.groovy.svg

class FontFace extends AbstractElementContainer<FontFace> {

  static final String NAME = 'font-face'

  FontFace(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
