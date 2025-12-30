package se.alipsa.groovy.svg

/**
 * SVG {@code <radialGradient>} element that defines a radial gradient paint.
 */
class RadialGradient extends Gradient<RadialGradient> {

  static final String NAME = 'radialGradient'

  /**
   * Creates a RadialGradient.
   *
   * @param parent value
   */
  RadialGradient(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   * Sets the cx attribute.
   *
   * @param cx value
   * @return this element for chaining
   */
  RadialGradient cx(Number cx) {
    addAttribute('cx', "$cx")
  }

  /**
   * Sets the cx attribute.
   *
   * @param cx value
   * @return this element for chaining
   */
  RadialGradient cx(String cx) {
    addAttribute('cx', cx)
  }

  String getCx() {
    getAttribute('cx')
  }

  /**
   * Sets the cy attribute.
   *
   * @param cy value
   * @return this element for chaining
   */
  RadialGradient cy(Number cy) {
    addAttribute('cy', "$cy")
  }

  /**
   * Sets the cy attribute.
   *
   * @param cy value
   * @return this element for chaining
   */
  RadialGradient cy(String cy) {
    addAttribute('cy', cy)
  }

  String getCy() {
    getAttribute('cy')
  }

  /**
   * Sets the r attribute.
   *
   * @param r value
   * @return this element for chaining
   */
  RadialGradient r(Number r) {
    addAttribute('r', "$r")
  }

  /**
   * Sets the r attribute.
   *
   * @param r value
   * @return this element for chaining
   */
  RadialGradient r(String r) {
    addAttribute('r', r)
  }

  String getR() {
    getAttribute('r')
  }

  /**
   * Sets the fx attribute.
   *
   * @param fx value
   * @return this element for chaining
   */
  RadialGradient fx(String fx) {
    addAttribute('fx', fx)
  }

  /**
   * Sets the fx attribute.
   *
   * @param fx value
   * @return this element for chaining
   */
  RadialGradient fx(Number fx) {
    addAttribute('fx', "$fx")
  }

  String getFx() {
    getAttribute('fx')
  }

  /**
   * Sets the fy attribute.
   *
   * @param fy value
   * @return this element for chaining
   */
  RadialGradient fy(String fy) {
    addAttribute('fy', fy)
  }

  /**
   * Sets the fy attribute.
   *
   * @param fy value
   * @return this element for chaining
   */
  RadialGradient fy(Number fy) {
    addAttribute('fy', "$fy")
  }

  String getFy() {
    getAttribute('fy')
  }
}
