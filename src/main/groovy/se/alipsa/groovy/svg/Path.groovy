package se.alipsa.groovy.svg

import groovy.transform.CompileStatic
import groovy.transform.PackageScope
import org.dom4j.Element

/**
 * SVG {@code <path>} element that defines shapes using path data commands.
 */
@CompileStatic
class Path extends AbstractShape<Path> implements Animatable<Path> {

  static final String NAME='path'

  @PackageScope
  Path(SvgElement parent) {
    super(parent, NAME)
  }

  @PackageScope
  Path(SvgElement parent, Element element) {
    super(parent, element)
  }

  /**
   * Sets the path data string (commands and coordinates defining the path geometry).
   *
   * @param d the path data
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
   * @param fill the fill color
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
   * @param value the value
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
   * @param width the width
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
