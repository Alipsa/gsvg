package se.alipsa.groovy.svg

/**
 * SVG {@code <meshGradient>} element that defines a mesh gradient paint.
 */
class MeshGradient extends AbstractElementContainer<MeshGradient> {

  static final String NAME = 'meshGradient'

  /**
   * Creates a MeshGradient.
   *
   * @param parent the parent SVG element
   */
  MeshGradient(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
