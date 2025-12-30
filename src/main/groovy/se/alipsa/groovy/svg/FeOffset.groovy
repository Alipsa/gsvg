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

  /**
   * Creates a FeOffset.
   *
   * @param parent value
   */
  FeOffset(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /** The horizontal shift position for the offset */
  FeOffset dx(String dx) {
    addAttribute('dx', "$dx")
  }
  /**
   * Returns the dx value.
   *
   * @return the dx value
   */
  String getDx() {
    getAttribute('dx')
  }

  /** The vertical shift position for the offset*/
  FeOffset dy(Number dy) {
    addAttribute('dy', "$dy")
  }

  /**
   * Returns the dx value.
   *
   * @return the dx value
   */
  String getDx() {
    getAttribute('dx')
  }

  /** The vertical shift position for the offset*/
  FeOffset dy(String dy) {
    addAttribute('dy', "$dy")
  }
  /**
   * Returns the dy value.
   *
   * @return the dy value
   */
  String getDy() {
    getAttribute('dy')
  }
}
