package se.alipsa.groovy.svg



import groovy.transform.PackageScope
import org.dom4j.Element
import groovy.transform.CompileStatic

/**
 * SVG {@code <meshGradient>} element that defines a mesh gradient paint.
 */
@CompileStatic
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

  @PackageScope
  MeshGradient(SvgElement parent, Element element) {
    super(parent, element)
  }

}