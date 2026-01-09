package se.alipsa.groovy.svg



import groovy.transform.PackageScope
import org.dom4j.Element
import groovy.transform.CompileStatic

/**
 * SVG {@code <meshrow>} element that defines a row of mesh patches.
 */
@CompileStatic
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

  @PackageScope
  MeshRow(SvgElement parent, Element element) {
    super(parent, element)
  }

}