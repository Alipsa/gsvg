package se.alipsa.groovy.svg

class Mesh extends SvgElement<Mesh> {

  static final String NAME = 'mesh'

  Mesh(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }
}
