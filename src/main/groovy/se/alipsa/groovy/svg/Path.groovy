package se.alipsa.groovy.svg

import groovy.transform.PackageScope
import org.dom4j.Element;

/**
 * SVG {@code <path>} element that defines shapes using path data commands.
 */
class Path extends AbstractShape<Path> implements Animatable<Path> {

  static final String NAME='path'

  @PackageScope
  Path(SvgElement parent) {
    super(parent, NAME)
  }

  /**
   * Sets the path data string (commands and coordinates defining the path geometry).
   *
   * @param d value
   * @return this element for chaining
   */
  Path d(String d) {
    addAttribute('d', d)
  }

  /**
   * Returns the path data string.
   *
   * @return the d value
   */
  String getD() {
    getAttribute('d')
  }

  /**
   * Sets the fill paint used to draw the path interior.
   *
   * @param fill value
   * @return this element for chaining
   */
  Path fill(String fill) {
    addAttribute('fill', fill)
  }

  /**
   * Returns the fill paint used to draw the path interior.
   *
   * @return the fill value
   */
  String getFill() {
    getAttribute('fill')
  }

  /**
   * Sets the stroke paint used to draw the path outline.
   *
   * @param value value
   * @return this element for chaining
   */
  Path stroke(String value) {
    addAttribute('stroke', value)
  }

  /**
   * Returns the stroke paint used to draw the path outline.
   *
   * @return the stroke value
   */
  String getStroke() {
    getAttribute('stroke')
  }

  /**
   * Sets the stroke width used to draw the path outline.
   *
   * @param width value
   * @return this element for chaining
   */
  Path strokeWidth(Number width) {
    addAttribute('stroke-width', width)
  }

  /**
   * Sets the stroke width used to draw the path outline.
   *
   * @param width value
   * @return this element for chaining
   */
  Path strokeWidth(String width) {
    addAttribute('stroke-width', width)
  }
  /**
   * Returns the stroke width used to draw the path outline.
   *
   * @return the stroke width value
   */
  String getStrokeWidth() {
    getAttribute('stroke-width')
  }
}
