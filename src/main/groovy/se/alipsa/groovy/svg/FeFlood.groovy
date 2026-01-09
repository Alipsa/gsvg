package se.alipsa.groovy.svg



import groovy.transform.PackageScope
import org.dom4j.Element
import groovy.transform.CompileStatic

/**
 * SVG {@code <feFlood>} filter primitive that fills the filter region with a solid color.
 */
@CompileStatic
class FeFlood extends  FilterElement<FeFlood> {
  static final String NAME = 'feFlood'

  /**
   * Creates a FeFlood.
   *
   * @param parent the parent SVG element
   */
  FeFlood(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  @PackageScope
  FeFlood(SvgElement parent, Element element) {
    super(parent, element)
  }


  /**
   * Sets the flood color attribute.
   *
   * @param color the color
   * @return this element for chaining
   */
  FeFlood floodColor(String color) {
    addAttribute('flood-color', color)
  }

  /**
   * Returns the flood color value.
   *
   * @return the flood color value
   */
  String getFloodColor() {
    getAttribute('flood-color')
  }

  /**
   * Sets the flood opacity attribute.
   *
   * @param opacity the opacity value (0.0 to 1.0)
   * @return this element for chaining
   */
  FeFlood floodOpacity(Number opacity) {
    addAttribute('flood-opacity', opacity)
  }

  /**
   * Sets the flood opacity attribute.
   *
   * @param opacity value
   * @return this element for chaining
   */
  FeFlood floodOpacity(String opacity) {
    addAttribute('flood-opacity', opacity)
  }
  /**
   * Returns the flood opacity value.
   *
   * @return the flood opacity value
   */
  String getFloodOpacity() {
    getAttribute('flood-opacity')
  }
}