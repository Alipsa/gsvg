package se.alipsa.groovy.svg

import groovy.transform.PackageScope

/**
 * SVG {@code <line>} element that draws a straight line between two points.
 */
class Line extends AbstractShape<Line> {

  static final String NAME='line'

  @PackageScope
  Line(SvgElement parent) {
    super(parent, NAME)
  }

  /**
   * <line x1="$x1" y1="$y1" x2="$x2" y2="$y2" style="$style" />
   * @param parent the parent dom4j element
   * @param x1 The start of the line on the x-axis
   * @param y1 The start of the line on the y-axis
   * @param x2 The end of the line on the x-axis
   * @param y2 The end of the line on the y-axis
   */
  @PackageScope
  Line(SvgElement parent, Number x1val, Number y1val, Number x2val, Number y2val) {
    this(parent)
    x1(x1val)
    y1(y1val)
    x2(x2val)
    y2(y2val)
  }

  /**
   * Sets the x-coordinate of the line start point (x1).
   *
   * @param value the value
   * @return this element for chaining
   */
  Line x1(Number value) {
    addAttribute('x1', value)
  }

  /**
   * Returns the x-coordinate of the line start point (x1).
   *
   * @return the x 1 value
   */
  String getX1() {
    getAttribute('x1')
  }

  /**
   * Sets the y-coordinate of the line start point (y1).
   *
   * @param value the value
   * @return this element for chaining
   */
  Line y1(Number value) {
    addAttribute('y1', value)
  }

  /**
   * Returns the y-coordinate of the line start point (y1).
   *
   * @return the y 1 value
   */
  String getY1() {
    getAttribute('y1')
  }

  /**
   * Sets the x-coordinate of the line end point (x2).
   *
   * @param value the value
   * @return this element for chaining
   */
  Line x2(Number value) {
    addAttribute('x2', value)
  }

  /**
   * Returns the x-coordinate of the line end point (x2).
   *
   * @return the x 2 value
   */
  String getX2() {
    getAttribute('x2')
  }

  /**
   * Sets the y-coordinate of the line end point (y2).
   *
   * @param value the value
   * @return this element for chaining
   */
  Line y2(Number value) {
    addAttribute('y2', value)
  }

  /**
   * Returns the y-coordinate of the line end point (y2).
   *
   * @return the y 2 value
   */
  String getY2() {
    getAttribute('y2')
  }

  /**
   * Sets the stroke paint used to draw the line.
   *
   * @param color the color
   * @return this element for chaining
   */
  Line stroke(String color) {
    addAttribute('stroke', color)
  }

  /**
   * Returns the stroke paint used to draw the line.
   *
   * @return the stroke value
   */
  String getStroke() {
    getAttribute('stroke')
  }

  /**
   * Sets the stroke width used to draw the line.
   *
   * @param width the width
   * @return this element for chaining
   */
  Line strokeWidth(Number width) {
    addAttribute('stroke-width', width)
  }

  /**
   * Returns the stroke width used to draw the line.
   *
   * @return the stroke width value
   */
  String getStrokeWidth() {
    getAttribute('stroke-width')
  }

  /**
   * Sets the marker reference to draw at the line start (for example `url(#markerId)`).
   *
   * @param start the start value
   * @return this element for chaining
   */
  Line markerStart(String start) {
    addAttribute('marker-start', start)
  }

  /**
   * Returns the marker reference drawn at the line start.
   *
   * @return the marker start value
   */
  String getMarkerStart() {
    getAttribute('marker-start')
  }

  /**
   * Sets the marker reference to draw at the line end (for example `url(#markerId)`).
   *
   * @param end the end time of the animation
   * @return this element for chaining
   */
  Line markerEnd(String end) {
    addAttribute('marker-end', end)
  }

  /**
   * Returns the marker reference drawn at the line end.
   *
   * @return the marker end value
   */
  String getMarkerEnd() {
    getAttribute('marker-end')
  }
}
