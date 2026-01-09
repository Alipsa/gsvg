package se.alipsa.groovy.svg



import groovy.transform.PackageScope
import org.dom4j.Element
import groovy.transform.CompileStatic

/**
 * SVG {@code <marker>} element that defines marker graphics for line ends or vertices.
 */
@CompileStatic
class Marker extends AbstractElementContainer<Marker> implements GradientContainer {

  static final String NAME = 'marker'

  /**
   * Creates a Marker.
   *
   * @param parent the parent SVG element
   */
  Marker(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  @PackageScope
  Marker(SvgElement parent, Element element) {
    super(parent, element)
  }


  /**
   * Sets the marker width attribute.
   *
   * @param width the width
   * @return this element for chaining
   */
  Marker markerWidth(Number width) {
    addAttribute('markerWidth', width)
  }

  /**
   * Sets the marker width attribute.
   *
   * @param width value
   * @return this element for chaining
   */
  Marker markerWidth(String width) {
    addAttribute('markerWidth', width)
  }
  /**
   * Returns the marker width value.
   *
   * @return the marker width value
   */
  String getMarkerWidth() {
    getAttribute('markerWidth')
  }

  /**
   * Sets the marker height attribute.
   *
   * @param height the height
   * @return this element for chaining
   */
  Marker markerHeight(Number height) {
    addAttribute('markerHeight', height)
  }

  /**
   * Sets the marker height attribute.
   *
   * @param height value
   * @return this element for chaining
   */
  Marker markerHeight(String height) {
    addAttribute('markerHeight', height)
  }
  /**
   * Returns the marker height value.
   *
   * @return the marker height value
   */
  String getMarkerHeight() {
    getAttribute('markerHeight')
  }

  /**
   * Sets the ref x attribute.
   *
   * @param refX the reference x-coordinate
   * @return this element for chaining
   */
  Marker refX(Number refX) {
    addAttribute('refX', refX)
  }

  /**
   * Sets the ref x attribute.
   *
   * @param refX value
   * @return this element for chaining
   */
  Marker refX(String refX) {
    addAttribute('refX', refX)
  }
  /**
   * Returns the ref x value.
   *
   * @return the ref x value
   */
  String getRefX() {
    getAttribute('refX')
  }

  /**
   * Sets the ref y attribute.
   *
   * @param refY the reference y-coordinate
   * @return this element for chaining
   */
  Marker refY(Number refY) {
    addAttribute('refY', refY)
  }

  /**
   * Sets the ref y attribute.
   *
   * @param refY value
   * @return this element for chaining
   */
  Marker refY(String refY) {
    addAttribute('refY', refY)
  }
  /**
   * Returns the ref y value.
   *
   * @return the ref y value
   */
  String getRefY() {
    getAttribute('refY')
  }

  /**
   * Sets the marker units attribute.
   *
   * @param units the coordinate system units
   * @return this element for chaining
   */
  Marker markerUnits(String units) {
    addAttribute('markerUnits', units)
    this
  }

  /**
   * Returns the marker units value.
   *
   * @return the marker units value
   */
  String getMarkerUnits() {
    getAttribute('markerUnits')
  }

  /**
   * Sets the orient attribute.
   *
   * @param value the value
   * @return this element for chaining
   */
  Marker orient(String value) {
    addAttribute('orient', value)
    this
  }

  /**
   * Returns the orient value.
   *
   * @return the orient value
   */
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