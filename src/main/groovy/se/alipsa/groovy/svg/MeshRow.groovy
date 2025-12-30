package se.alipsa.groovy.svg

/**
 * SVG {@code <meshrow>} element that defines a row of mesh patches.
 */
class MeshRow extends AbstractElementContainer<MeshRow> {

  static final String NAME = 'meshrow'

  /**
   * Creates a MeshRow.
   *
   * @param parent the parent SVG element
   */
  MeshRow(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
