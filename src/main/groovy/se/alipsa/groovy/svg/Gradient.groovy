package se.alipsa.groovy.svg

import groovy.transform.CompileStatic
import org.dom4j.Element

/**
 * Base class for gradient elements that define color transitions.
 */
@CompileStatic
abstract class Gradient<T extends Gradient<T>> extends SvgElement<T> {

  /**
   * Creates a Gradient.
   *
   * @param parent the parent SVG element
   * @param name the name of the element
   */
  Gradient(SvgElement<? extends SvgElement> parent, String name) {
    super(parent, name)
  }

  /**
   * Creates a Gradient by adopting an existing DOM4J Element.
   * Used for cloning/copying operations.
   *
   * @param parent the parent SVG element
   * @param element the DOM4J element to adopt
   */
  Gradient(SvgElement parent, Element element) {
    super(parent, element)
  }

  /**
   * Creates and adds a new Stop child element.
   *
   * @return the created element
   */
  Stop addStop() {
    add(new Stop(this))
  }

  /**
   * Sets the gradient units attribute.
   *
   * @param units the coordinate system units
   * @return this element for chaining
   */
  T gradientUnits(String units) {
    addAttribute('gradientUnits', units)
    this as T
  }

  /**
   * Sets the gradient transform attribute.
   *
   * @param transform the transformation matrix
   * @return this element for chaining
   */
  T gradientTransform(String transform) {
    addAttribute('gradientTransform', transform)
    this as T
  }

  /**
   * Sets the spread method attribute.
   *
   * @param method the interpolation method
   * @return this element for chaining
   */
  T spreadMethod(String method) {
    addAttribute('spreadMethod', method)
    this as T
  }

  /**
   * Returns the gradient units value.
   *
   * @return the gradient units value
   */
  String getGradientUnits() {
    getAttribute('gradientUnits')
  }

  /**
   * Returns the gradient transform value.
   *
   * @return the gradient transform value
   */
  String getGradientTransform() {
    getAttribute('gradientTransform')
  }

  /**
   * Returns the spread method value.
   *
   * @return the spread method value
   */
  String getSpreadMethod() {
    getAttribute('spreadMethod')
  }

  /**
   * Returns the stops value.
   *
   * @return the stops value
   */
  List<Stop> getStops() {
    children.findAll {it instanceof Stop} as List<Stop>
  }
}
