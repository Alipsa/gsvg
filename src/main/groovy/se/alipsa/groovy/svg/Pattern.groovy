package se.alipsa.groovy.svg

/**
 * SVG {@code <pattern>} element that defines a tiled pattern paint.
 */
class Pattern extends AbstractElementContainer<Pattern> {

  static final String NAME='pattern'

  /**
   * Creates a Pattern.
   *
   * @param parent value
   */
  Pattern(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   * Sets the x attribute.
   *
   * @param x value
   * @return this element for chaining
   */
  Pattern x(Number x) {
    addAttribute('x', String.valueOf(x))
  }

  String getX() {
    getAttribute('x')
  }

  /**
   * Sets the y attribute.
   *
   * @param y value
   * @return this element for chaining
   */
  Pattern y(Number y) {
    addAttribute('y', String.valueOf(y))
  }

  String getY() {
    getAttribute('y')
  }

  /**
   * Sets the width attribute.
   *
   * @param width value
   * @return this element for chaining
   */
  Pattern width(Number width) {
    addAttribute('width', "$width")
  }

  String getWidth() {
    getAttribute('width')
  }

  /**
   * Sets the height attribute.
   *
   * @param height value
   * @return this element for chaining
   */
  Pattern height(Number height) {
    addAttribute('height', "$height")
  }

  String getHeight() {
    getAttribute('height')
  }

  /**
   * Sets the pattern units attribute.
   *
   * @param units value
   * @return this element for chaining
   */
  Pattern patternUnits(String units) {
    addAttribute('patternUnits', units)
  }

  String getPatternUnits() {
    getAttribute('patternUnits')
  }
}
