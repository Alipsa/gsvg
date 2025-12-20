package se.alipsa.groovy.svg

class FontFaceUri extends SvgElement<FontFaceUri> {

  static final String NAME = 'font-face-uri'

  FontFaceUri(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
