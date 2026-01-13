package se.alipsa.groovy.svg



import groovy.transform.PackageScope
import org.dom4j.Element
import groovy.transform.CompileStatic
import se.alipsa.groovy.svg.utils.ViewBox

/**
 * SVG {@code <view>} element that defines a named view with viewBox settings.
 */
@CompileStatic
class View extends SvgElement<View> {

  static final String NAME = 'view'

  /**
   * Creates a View.
   *
   * @param parent the parent SVG element
   */
  View(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  @PackageScope
  View(SvgElement parent, Element element) {
    super(parent, element)
  }


  /**
   * Sets the view box attribute.
   *
   * @param value the value
   * @return this element for chaining
   */
  View viewBox(String value) {
    addAttribute('viewBox', value)
  }

  /**
   * Returns the view box value.
   *
   * @return the view box value
   */
  String getViewBox() {
    getAttribute('viewBox')
  }

  /**
   * Sets the view box from a ViewBox object.
   *
   * @param viewBox the ViewBox object
   * @return this element for chaining
   */
  View viewBox(ViewBox viewBox) {
    addAttribute('viewBox', viewBox.toString())
  }

  /**
   * Sets the view box with four numeric parameters (convenience method).
   *
   * @param minX the minimum x coordinate
   * @param minY the minimum y coordinate
   * @param width the viewport width
   * @param height the viewport height
   * @return this element for chaining
   */
  View viewBox(Number minX, Number minY, Number width, Number height) {
    viewBox(ViewBox.of(minX, minY, width, height))
  }

  /**
   * Parses and returns the current viewBox as a ViewBox object.
   * @return the parsed ViewBox, or null if not set
   */
  ViewBox getViewBoxObject() {
    String vb = getViewBox()
    vb ? ViewBox.parse(vb) : null
  }

  /**
   * Sets the preserve aspect ratio attribute.
   *
   * @param value the value
   * @return this element for chaining
   */
  View preserveAspectRatio(String value) {
    addAttribute('preserveAspectRatio', value)
  }

  /**
   * Returns the preserve aspect ratio value.
   *
   * @return the preserve aspect ratio value
   */
  String getPreserveAspectRatio() {
    getAttribute('preserveAspectRatio')
  }
}