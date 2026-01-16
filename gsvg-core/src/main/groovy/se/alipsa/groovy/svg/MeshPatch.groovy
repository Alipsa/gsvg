package se.alipsa.groovy.svg



import groovy.transform.PackageScope
import org.dom4j.Element
import groovy.transform.CompileStatic

/**
 * SVG {@code <meshpatch>} element that defines a single patch in a mesh gradient.
 */
@CompileStatic
class MeshPatch extends SvgElement<MeshPatch> {

  static final String NAME = 'meshpatch'

  /**
   * Creates a MeshPatch.
   *
   * @param parent the parent SVG element
   */
  MeshPatch(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  @PackageScope
  MeshPatch(SvgElement parent, Element element) {
    super(parent, element)
  }

}