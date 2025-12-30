package se.alipsa.groovy.svg

/**
 * SVG {@code <feDiffuseLighting>} filter primitive that computes diffuse lighting.
 */
class FeDiffuseLighting extends FilterElement<FeDiffuseLighting> implements LightSourceContainer<FeDiffuseLighting> {

  static final String NAME='feDiffuseLighting'

  /**
   * Creates a FeDiffuseLighting.
   *
   * @param parent value
   */
  FeDiffuseLighting(SvgElement parent) {
    super(parent, NAME)
  }

}
