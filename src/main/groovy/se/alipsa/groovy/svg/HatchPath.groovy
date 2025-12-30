package se.alipsa.groovy.svg

/**
 * SVG {@code <hatchpath>} element that defines a hatch path used by hatch.
 */
class HatchPath extends SvgElement<HatchPath> {

  static final String NAME = 'hatchpath'

  /**
   * Creates a HatchPath.
   *
   * @param parent value
   */
  HatchPath(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
