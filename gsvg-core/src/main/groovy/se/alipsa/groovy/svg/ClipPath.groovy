package se.alipsa.groovy.svg

import groovy.transform.CompileStatic
import org.dom4j.Element

/**
 * SVG {@code <clipPath>} element that defines a clipping path for other elements.
 */
@CompileStatic
class ClipPath extends AbstractElementContainer<ClipPath> implements Animatable<ClipPath> {

  static final String NAME='clipPath'

  /**
   * Creates a ClipPath.
   *
   * @param parent the parent SVG element
   */
  ClipPath(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   * Creates a ClipPath by adopting an existing DOM4J Element.
   * Used for cloning/copying operations.
   *
   * @param parent the parent SVG element
   * @param element the DOM4J element to adopt
   */
  ClipPath(SvgElement parent, Element element) {
    super(parent, element)
  }
}
