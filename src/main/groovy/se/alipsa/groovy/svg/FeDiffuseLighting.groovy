package se.alipsa.groovy.svg

import groovy.transform.CompileStatic

/**
 * SVG {@code <feDiffuseLighting>} filter primitive that computes diffuse lighting.
 */
@CompileStatic
class FeDiffuseLighting extends FilterElement<FeDiffuseLighting> implements LightSourceContainer<FeDiffuseLighting> {

  static final String NAME='feDiffuseLighting'

  /**
   * Creates a FeDiffuseLighting.
   *
   * @param parent the parent SVG element
   */
  FeDiffuseLighting(SvgElement parent) {
    super(parent, NAME)
  }

}
