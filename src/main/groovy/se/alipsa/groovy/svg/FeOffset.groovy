package se.alipsa.groovy.svg

/**
 * SVG {@code <feOffset>} filter primitive that offsets the input image.
 */
class FeOffset extends FilterElement<FeOffset> {

  static final String NAME = 'feOffset'

  /**
   * Creates a FeOffset.
   *
   * @param parent value
   */
  FeOffset(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /** The horizontal shift position for the offset */
  FeOffset dx(Number dx) {
    addAttribute('dx', "$dx")
  }

  String getDx() {
    getAttribute('dx')
  }

  /** The vertical shift position for the offset*/
  FeOffset dy(Number dy) {
    addAttribute('dy', "$dy")
  }

  String getDy() {
    getAttribute('dy')
  }
}
