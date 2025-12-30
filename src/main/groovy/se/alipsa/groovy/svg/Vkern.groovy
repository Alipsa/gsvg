package se.alipsa.groovy.svg

/**
 * SVG {@code <vkern>} element that defines vertical kerning adjustments.
 */
class Vkern extends SvgElement<Vkern> {

  static final String NAME = 'vkern'

  /**
   * Creates a Vkern.
   *
   * @param parent value
   */
  Vkern(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
