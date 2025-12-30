package se.alipsa.groovy.svg

/**
 * SVG {@code <clipPath>} element that defines a clipping path for other elements.
 */
class ClipPath extends AbstractElementContainer<ClipPath> implements Animatable<ClipPath> {

  static final String NAME='clipPath'

  /**
   * Creates a ClipPath.
   *
   * @param parent value
   */
  ClipPath(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
