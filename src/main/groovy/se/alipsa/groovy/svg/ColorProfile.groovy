package se.alipsa.groovy.svg

import groovy.transform.CompileStatic

/**
 * SVG {@code <color-profile>} element that declares an ICC color profile.
 */
@CompileStatic
class ColorProfile extends SvgElement<ColorProfile> {

  static final String NAME = 'color-profile'

  /**
   * Creates a ColorProfile.
   *
   * @param parent the parent SVG element
   */
  ColorProfile(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
