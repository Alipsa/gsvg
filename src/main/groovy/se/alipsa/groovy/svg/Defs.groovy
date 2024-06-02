package se.alipsa.groovy.svg

class Defs extends AbstractElementContainer<Defs> implements GradientContainer {

  static final String NAME = 'defs'

  Defs(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  Marker addMarker() {
    add(new Marker(this))
  }

  Marker addMarker(String id) {
    addMarker().id(id)
  }

  Pattern addPattern() {
    add(new Pattern(this))
  }

  Pattern addPattern(String id) {
    add(new Pattern(this).id(id))
  }
}
