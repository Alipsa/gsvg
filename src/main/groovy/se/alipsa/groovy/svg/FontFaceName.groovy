package se.alipsa.groovy.svg

class FontFaceName extends SvgElement<FontFaceName> {

  static final String NAME = 'font-face-name'

  FontFaceName(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
