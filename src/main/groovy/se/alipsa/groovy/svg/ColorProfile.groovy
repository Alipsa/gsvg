package se.alipsa.groovy.svg

/**
 * SVG {@code <color-profile>} element that declares an ICC color profile.
 */
class ColorProfile extends SvgElement<ColorProfile> {

  static final String NAME = 'color-profile'

  /**
   * Creates a ColorProfile.
   *
   * @param parent value
   */
  ColorProfile(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
