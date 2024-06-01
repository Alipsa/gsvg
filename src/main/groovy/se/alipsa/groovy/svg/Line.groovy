package se.alipsa.groovy.svg

import groovy.transform.PackageScope

class Line extends SvgElement {

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

  Line x1(Number value) {
    addAttribute('x1', value)
  }

  String getX1() {
    getAttribute('x1')
  }

  Line y1(Number value) {
    addAttribute('y1', value)
  }

  String getY1() {
    getAttribute('y1')
  }

  Line x2(Number value) {
    addAttribute('x2', value)
  }

  String getX2() {
    getAttribute('x2')
  }

  Line y2(Number value) {
    addAttribute('y2', value)
  }

  String getY2() {
    getAttribute('y2')
  }

  Line stroke(String color) {
    addAttribute('stroke', color)
  }

  String getStroke() {
    getAttribute('stroke')
  }

  Line strokeWidth(Number width) {
    addAttribute('stroke-width', width)
  }

  String getStrokeWidth() {
    getAttribute('stroke-width')
  }

  Line markerStart(String start) {
    addAttribute('marker-start', start)
  }

  String getMarkerStart() {
    getAttribute('marker-start')
  }

  Line markerEnd(String end) {
    addAttribute('marker-end', end)
  }

  String getMarkerEnd() {
    getAttribute('marker-end')
  }
}
