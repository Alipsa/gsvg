package se.alipsa.groovy.svg

/**
 * SVG {@code <meshpatch>} element that defines a single patch in a mesh gradient.
 */
class MeshPatch extends SvgElement<MeshPatch> {

  static final String NAME = 'meshpatch'

  /**
   * Creates a MeshPatch.
   *
   * @param parent value
   */
  MeshPatch(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
