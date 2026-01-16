package se.alipsa.groovy.svg

import groovy.transform.CompileStatic;
import org.dom4j.Element

/**
 * SVG {@code <linearGradient>} element that defines a linear gradient paint.
 */
@CompileStatic
class LinearGradient extends Gradient<LinearGradient> {

  static final String NAME = 'linearGradient'

  /**
   * Creates a LinearGradient.
   *
   * @param parent the parent SVG element
   */
  LinearGradient(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   * Creates a LinearGradient by adopting an existing DOM4J Element.
   * Used for cloning/copying operations.
   *
   * @param parent the parent SVG element
   * @param element the DOM4J element to adopt
   */
  LinearGradient(SvgElement parent, Element element) {
    super(parent, element)
  }

  /**
   * Sets the x 1 attribute.
   *
   * @param x1 the starting x-coordinate
   * @return this element for chaining
   */
  LinearGradient x1(String x1) {
    addAttribute('x1', x1)
  }

  /**
   * Sets the x 1 attribute.
   *
   * @param x1 the starting x-coordinate
   * @return this element for chaining
   */
  LinearGradient x1(Number x1) {
    addAttribute('x1', x1)
  }

  /**
   * Returns the x 1 value.
   *
   * @return the x 1 value
   */
  String getX1() {
    getAttribute('x1')
  }

  /**
   * Sets the x 2 attribute.
   *
   * @param x2 the ending x-coordinate
   * @return this element for chaining
   */
  LinearGradient x2(String x2) {
    addAttribute('x2', x2)
  }

  /**
   * Sets the x 2 attribute.
   *
   * @param x2 the ending x-coordinate
   * @return this element for chaining
   */
  LinearGradient x2(Number x2) {
    addAttribute('x2', x2)
  }

  /**
   * Returns the x 2 value.
   *
   * @return the x 2 value
   */
  String getX2() {
    getAttribute('x2')
  }

  /**
   * Sets the y 1 attribute.
   *
   * @param y1 the starting y-coordinate
   * @return this element for chaining
   */
  LinearGradient y1(String y1) {
    addAttribute('y1', y1)
  }

  /**
   * Sets the y 1 attribute.
   *
   * @param y1 the starting y-coordinate
   * @return this element for chaining
   */
  LinearGradient y1(Number y1) {
    addAttribute('y1', y1)
  }

  /**
   * Returns the y 1 value.
   *
   * @return the y 1 value
   */
  String getY1() {
    getAttribute('y1')
  }

  /**
   * Sets the y 2 attribute.
   *
   * @param y2 the ending y-coordinate
   * @return this element for chaining
   */
  LinearGradient y2(String y2) {
    addAttribute('y2', y2)
  }

  /**
   * Sets the y 2 attribute.
   *
   * @param y2 the ending y-coordinate
   * @return this element for chaining
   */
  LinearGradient y2(Number y2) {
    addAttribute('y2', y2)
  }

  /**
   * Returns the y 2 value.
   *
   * @return the y 2 value
   */
  String getY2() {
    getAttribute('y2')
  }

}
