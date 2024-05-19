package se.alipsa.groovy.svg

class Defs extends AbstractElementContainer<Defs> implements GradientContainer {

  static final String NAME = 'defs'

  Defs(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  Marker addMarker() {
    Marker marker = add(new Marker(this))
    marker
  }

  Marker addMarker(String id) {
    addMarker().id(id)
  }
}
