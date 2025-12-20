package se.alipsa.groovy.svg

class ColorProfile extends SvgElement<ColorProfile> {

  static final String NAME = 'color-profile'

  ColorProfile(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
