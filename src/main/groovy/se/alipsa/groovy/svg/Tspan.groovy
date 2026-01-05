package se.alipsa.groovy.svg

import groovy.transform.CompileStatic

/**
 * SVG {@code <tspan>} element that positions and styles a span of text.
 */
@CompileStatic
class Tspan extends StringContentContainer<Tspan> {

  static final String NAME='tspan'

  /**
   * Creates a Tspan.
   *
   * @param parent the parent SVG element
   */
  Tspan(SvgElement parent) {
    super(parent, NAME)
  }

  /**
   * Creates a Tspan.
   *
   * @param parent the parent SVG element
   * @param text the text value to add
   */
  Tspan(SvgElement parent, String text) {
    this(parent)
    addContent(text)
  }

  /**
   * The x position of the start of the text. Default is 0.
   *
   * @param x value
   * @return this element for chaining
   */
  Tspan x(Number x) {
    addAttribute('x', "$x")
  }

  /**
   * The x position of the start of the text. Default is 0.
   *
   * @param x the x value
   * @return this element for chaining
   */
  Tspan x(String x) {
    addAttribute('x', "$x")
  }

  /**
   * The y position of the start of the text. Default is 0.
   *
   * @param y the y value
   * @return this element for chaining
   */
  Tspan y(Number y) {
    addAttribute('y', "$y")
  }

  /**
   * The y position of the start of the text. Default is 0.
   *
   * @param y the y-coordinate of the text
   * @return this element for chaining
   */
  Tspan y(String y) {
    addAttribute('y', "$y")
  }

  /**
   * The horizontal shift position for text (from previous text position).
   *
   * @param dx the dx value(s)
   * @return this element for chaining
   */
  Tspan dx(Number... dx) {
    addAttribute('dx', String.join(',', dx.collect {it as String}))
  }

  /**
   * The horizontal shift position for text (from previous text position).
   *
   * @param dx the dx value(s)
   * @return this element for chaining
   */
  Tspan dx(String... dx) {
    addAttribute('dx', String.join(',', dx.collect {it as String}))
  }

  /**
   * The vertical shift position for text (from previous text position).
   *
   * @param dy the dy value(s)
   * @return this element for chaining
   */
  Tspan dy(Number... dy) {
    addAttribute('dy', String.join(',', dy.collect {it as String}))
  }

  /**
   * The vertical shift position for text (from previous text position).
   *
   * @param dy the dy value(s)
   * @return this element for chaining
   */
  Tspan dy(String... dy) {
    addAttribute('dy', String.join(',', dy.collect {it as String}))
  }

  /**
   * The rotation (in degrees) applied to each letter of text.
   *
   * @param rotate value
   * @return this element for chaining
   */
  Tspan rotate(Number rotate) {
    addAttribute('rotate', "$rotate")
  }

  /**
   * The rotation (in degrees) applied to each letter of text.
   *
   * @param rotate value
   * @return this element for chaining
   */
  Tspan rotate(String rotate) {
    addAttribute('rotate', "$rotate")
  }

  /**
   * The width that the text must fit in.
   *
   * @param textLength the text length (width) value
   * @return this element for chaining
   */
  Tspan textLength(Number textLength) {
    addAttribute('textLength', "$textLength")
  }

  /**
   * The width that the text must fit in.
   *
   * @param textLength the text length (width) value
   * @return this element for chaining
   */
  Tspan textLength(String textLength) {
    addAttribute('textLength', "$textLength")
  }

  /**
   * Sets the fill attribute.
   *
   * @param fill the fill value
   * @return this element for chaining
   */
  Tspan fill(String fill) {
    addAttribute('fill', fill)
  }

  /**
   * Sets the stroke attribute.
   *
   * @param stroke the stroke value
   * @return this element for chaining
   */
  Tspan stroke(String stroke) {
    addAttribute('stroke', stroke)
  }

  /**
   * Returns the stroke value.
   *
   * @return the stroke value
   */
  String getStroke() {
    getAttribute('stroke')
  }
}
