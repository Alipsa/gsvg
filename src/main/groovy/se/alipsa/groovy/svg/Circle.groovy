package se.alipsa.groovy.svg

import groovy.transform.PackageScope

class Circle extends AbstractShape<Circle> {

  static final String NAME='circle'

  @PackageScope
  Circle(SvgElement parent) {
    super(parent,NAME)
  }

  Circle cx(Number cx) {
    addAttribute('cx', cx)
  }

  Circle cx(String cx) {
    addAttribute('cx', cx)
  }

  String getCx() {
    getAttribute('cx')
  }

  Circle cy(Number cy) {
    addAttribute('cy', cy)
  }

  Circle cy(String cy) {
    addAttribute('cy', cy)
  }

  String getCy() {
    getAttribute('cy')
  }

  Circle r(Number r) {
    addAttribute('r', "$r")
  }

  String getR() {
    getAttribute('r')
  }

  Circle stroke(String stroke) {
    addAttribute('stroke', "$stroke")
  }

  String getStroke() {
    getAttribute('stroke')
  }

  Circle strokeWidth(Number strokeWidth) {
    addAttribute('stroke-width', "$strokeWidth")
  }

  String getStrokeWidth() {
    getAttribute('stroke-width')
  }

  Circle fill(String fill) {
    addAttribute('fill', "$fill")
  }

  String getFill() {
    getAttribute('fill')
  }
}
