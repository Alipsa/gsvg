package se.alipsa.groovy.svg

import groovy.transform.CompileStatic

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
}
