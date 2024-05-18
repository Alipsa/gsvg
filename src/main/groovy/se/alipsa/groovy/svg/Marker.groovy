package se.alipsa.groovy.svg

class Marker extends AbstractElementContainer<Marker> implements LinearGradientContainer {

  static final String NAME = 'marker'

  Marker(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  Marker markerWidth(Number width) {
    addAttribute('markerWidth', width)
  }

  String getMarkerWidth() {
    getAttribute('markerWidth')
  }

  Marker markerHeight(Number height) {
    addAttribute('markerHeight', height)
  }

  String getMarkerHeight() {
    getAttribute('markerHeight')
  }

  Marker refX(Number refX) {
    addAttribute('refX', refX)
  }

  String getRefX() {
    getAttribute('refX')
  }

  Marker refY(Number refY) {
    addAttribute('refY', refY)
  }

  String getRefY() {
    getAttribute('refY')
  }

  @Override
  String toString() {
    return "Marker(id=${getAttribute('id')})"
  }
}
