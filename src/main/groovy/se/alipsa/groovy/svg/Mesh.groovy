package se.alipsa.groovy.svg

/**
 * SVG {@code <mesh>} element that defines a mesh grid for mesh gradients.
 */
class Mesh extends SvgElement<Mesh> {

  static final String NAME = 'mesh'

  /**
   * Creates a Mesh.
   *
   * @param parent value
   */
  Mesh(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
