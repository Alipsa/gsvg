package se.alipsa.groovy.svg

class RadialGradient extends Gradient<RadialGradient> {

  static final String NAME = 'radialGradient'

  RadialGradient(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  RadialGradient cx(Number cx) {
    addAttribute('cx', "$cx")
  }

  RadialGradient cx(String cx) {
    addAttribute('cx', cx)
  }

  String getCx() {
    getAttribute('cx')
  }

  RadialGradient cy(Number cy) {
    addAttribute('cy', "$cy")
  }

  RadialGradient cy(String cy) {
    addAttribute('cy', cy)
  }

  String getCy() {
    getAttribute('cy')
  }

  RadialGradient r(Number r) {
    addAttribute('r', "$r")
  }

  RadialGradient r(String r) {
    addAttribute('r', r)
  }

  String getR() {
    getAttribute('r')
  }

  RadialGradient fx(String fx) {
    addAttribute('fx', fx)
  }

  RadialGradient fx(Number fx) {
    addAttribute('fx', "$fx")
  }

  String getFx() {
    getAttribute('fx')
  }

  RadialGradient fy(String fy) {
    addAttribute('fy', fy)
  }

  RadialGradient fy(Number fy) {
    addAttribute('fy', "$fy")
  }

  String getFy() {
    getAttribute('fy')
  }
}
