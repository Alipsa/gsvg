package se.alipsa.groovy.svg

/**
 * SVG {@code <hatch>} element that defines a hatch pattern paint.
 */
class Hatch extends AbstractElementContainer<Hatch> {

  static final String NAME = 'hatch'

  /**
   * Creates a Hatch.
   *
   * @param parent value
   */
  Hatch(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
