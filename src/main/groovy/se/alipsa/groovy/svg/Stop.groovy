package se.alipsa.groovy.svg

import groovy.transform.CompileStatic
import org.dom4j.Element

/**
 * SVG {@code <stop>} element that defines a color stop in a gradient.
 */
@CompileStatic
class Stop extends SvgElement<Stop> {

  static final String NAME = 'stop'

  /**
   * Creates a Stop.
   *
   * @param parent the parent SVG element
   */
  Stop(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   * Creates a Stop by adopting an existing DOM4J Element.
   * Used for cloning/copying operations.
   *
   * @param parent the parent SVG element
   * @param element the DOM4J element to adopt
   */
  Stop(SvgElement parent, Element element) {
    super(parent, element)
  }

  /**
   * Sets the offset attribute.
   *
   * @param offset the offset
   * @return this element for chaining
   */
  Stop offset(String offset) {
    addAttribute("offset", offset)
  }

  /**
   * Sets the offset attribute.
   *
   * @param offset the offset
   * @return this element for chaining
   */
  Stop offset(Number offset) {
    addAttribute("offset", offset)
  }

  /**
   * Returns the offset value.
   *
   * @return the offset value
   */
  String getOffset() {
    getAttribute("offset")
  }

  /**
   * Sets the stop color attribute.
   *
   * @param color the color
   * @return this element for chaining
   */
  Stop stopColor(String color) {
    addAttribute("stop-color", color)
  }

  /**
   * Returns the stop color value.
   *
   * @return the stop color value
   */
  String getStopColor() {
    getAttribute("stop-color")
  }

  /**
   * Sets the stop opacity attribute.
   *
   * @param opacity the opacity value (0-1)
   * @return this element for chaining
   */
  Stop stopOpacity(String opacity) {
    addAttribute("stop-opacity", opacity)
  }

  /**
   * Sets the stop opacity attribute.
   *
   * @param opacity the opacity value (0-1)
   * @return this element for chaining
   */
  Stop stopOpacity(Number opacity) {
    addAttribute("stop-opacity", opacity)
  }

  /**
   * Returns the stop opacity value.
   *
   * @return the stop opacity value
   */
  String getStopOpacity() {
    getAttribute("stop-opacity")
  }
}
