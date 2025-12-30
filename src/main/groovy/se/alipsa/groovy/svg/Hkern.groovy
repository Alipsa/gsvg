package se.alipsa.groovy.svg

/**
 * SVG {@code <hkern>} element that defines horizontal kerning adjustments.
 */
class Hkern extends SvgElement<Hkern> {

  static final String NAME = 'hkern'

  /**
   * Creates a Hkern.
   *
   * @param parent the parent SVG element
   */
  Hkern(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
