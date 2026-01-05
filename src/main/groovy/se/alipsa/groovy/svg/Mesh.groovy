package se.alipsa.groovy.svg

import groovy.transform.CompileStatic

/**
 * SVG {@code <mesh>} element that defines a mesh grid for mesh gradients.
 */
@CompileStatic
class Mesh extends SvgElement<Mesh> {

  static final String NAME = 'mesh'

  /**
   * Creates a Mesh.
   *
   * @param parent the parent SVG element
   */
  Mesh(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
