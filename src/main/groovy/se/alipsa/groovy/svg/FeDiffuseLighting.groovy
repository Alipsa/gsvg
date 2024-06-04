package se.alipsa.groovy.svg

class FeDiffuseLighting extends FilterElement<FeDiffuseLighting> implements LightSourceContainer<FeDiffuseLighting> {

  static final String NAME='feDiffuseLighting'

  FeDiffuseLighting(SvgElement parent) {
    super(parent, NAME)
  }

}
