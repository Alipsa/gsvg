package se.alipsa.groovy.svg

import groovy.transform.CompileStatic

/**
 * SVG {@code <feOffset>} filter primitive that offsets the input image.
 */
@CompileStatic
class FeOffset extends FilterElement<FeOffset> {

  static final String NAME = 'feOffset'

  /**
   * Creates a FeOffset.
   *
   * @param parent the parent SVG element
   */
  FeOffset(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /** The horizontal shift position for the offset */
  FeOffset dx(Number dx) {
    addAttribute('dx', "$dx")
  }

  /**
   * The horizontal shift position for the offset
   *
   * @param dx the horizontal shift position for the offset
   * @return this element for chaining
   */
  FeOffset dx(String dx) {
    addAttribute('dx', dx)
  }

  /**
   * Returns the dx value.
   *
   * @return the dx value
   */
  String getDx() {
    getAttribute('dx')
  }

  /**
   * The vertical shift position for the offset
   *
   * @param dy the vertical shift position for the offset
   * @return this element for chaining
   */
  FeOffset dy(Number dy) {
    addAttribute('dy', "$dy")
  }

  /**
   * The vertical shift position for the offset
   *
   * @param dy the vertical shift position for the offset
   * @return this element for chaining
   */
  FeOffset dy(String dy) {
    addAttribute('dy', dy)
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
