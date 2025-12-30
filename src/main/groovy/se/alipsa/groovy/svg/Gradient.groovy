package se.alipsa.groovy.svg

/**
 * Base class for gradient elements that define color transitions.
 */
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
   * Returns the stops value.
   *
   * @return the stops value
   */
  List<Stop> getStops() {
    children.findAll {it instanceof Stop} as List<Stop>
  }
}
