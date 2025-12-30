package se.alipsa.groovy.svg

/**
 * SVG {@code <marker>} element that defines marker graphics for line ends or vertices.
 */
class Marker extends AbstractElementContainer<Marker> implements GradientContainer {

  static final String NAME = 'marker'

  /**
   * Creates a Marker.
   *
   * @param parent value
   */
  Marker(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   * Sets the marker width attribute.
   *
   * @param width value
   * @return this element for chaining
   */
  Marker markerWidth(Number width) {
    addAttribute('markerWidth', width)
  }

  String getMarkerWidth() {
    getAttribute('markerWidth')
  }

  /**
   * Sets the marker height attribute.
   *
   * @param height value
   * @return this element for chaining
   */
  Marker markerHeight(Number height) {
    addAttribute('markerHeight', height)
  }

  String getMarkerHeight() {
    getAttribute('markerHeight')
  }

  /**
   * Sets the ref x attribute.
   *
   * @param refX value
   * @return this element for chaining
   */
  Marker refX(Number refX) {
    addAttribute('refX', refX)
  }

  String getRefX() {
    getAttribute('refX')
  }

  /**
   * Sets the ref y attribute.
   *
   * @param refY value
   * @return this element for chaining
   */
  Marker refY(Number refY) {
    addAttribute('refY', refY)
  }

  String getRefY() {
    getAttribute('refY')
  }

  /**
   * Sets the marker units attribute.
   *
   * @param units value
   * @return this element for chaining
   */
  Marker markerUnits(String units) {
    addAttribute('markerUnits', units)
    this
  }

  String getMarkerUnits() {
    getAttribute('markerUnits')
  }

  /**
   * Sets the orient attribute.
   *
   * @param value value
   * @return this element for chaining
   */
  Marker orient(String value) {
    addAttribute('orient', value)
    this
  }

  String getOrient() {
    getAttribute('orient')
  }

  /**
   * To string.
   *
   * @return the result
   */
  @Override
  String toString() {
    return "Marker(id=${getAttribute('id')})"
  }
}
