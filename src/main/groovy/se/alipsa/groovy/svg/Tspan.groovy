package se.alipsa.groovy.svg

/**
 * SVG {@code <tspan>} element that positions and styles a span of text.
 */
class Tspan extends StringContentContainer<Tspan> {

  static final String NAME='tspan'

  /**
   * Creates a Tspan.
   *
   * @param parent value
   */
  Tspan(SvgElement parent) {
    super(parent, NAME)
  }

  /**
   * Creates a Tspan.
   *
   * @param parent value
   * @param text value
   */
  Tspan(SvgElement parent, String text) {
    this(parent)
    addContent(text)
  }

  /**The x position of the start of the text. Default is 0 */
  Tspan x(Number x) {
    addAttribute('x', "$x")
  }

  /** The y position of the start of the text. Default is 0 */
  Tspan y(Number y) {
    addAttribute('y', "$y")
  }
  /**
   * Text.
   *
   * @param position value
   * @return the result
   */
  /** The horizontal shift position for text (from previous text position) */
  Tspan dx(Number... dx) {
    addAttribute('dx', String.join(',', dx.collect {it as String}))
  }

  /**
   * Text.
   *
   * @param position value
   * @return the result
   */
  /** The vertical shift position for text (from previous text position)*/
  Tspan dy(Number... dy) {
    addAttribute('dy', String.join(',', dy.collect {it as String}))
  }

  /**
   * Rotation.
   *
   * @param degrees value
   * @return the result
   */
  /** The rotation (in degrees) applied to each letter of text */
  Tspan rotate(Number rotate) {
    addAttribute('rotate', "$rotate")
  }

  /** The width that the text must fit in */
  Tspan textLength(Number textLength) {
    addAttribute('textLength', "$textLength")
  }

  /**
   * Sets the fill attribute.
   *
   * @param fill value
   * @return this element for chaining
   */
  Tspan fill(String fill) {
    addAttribute('fill', fill)
  }

  /**
   * Sets the stroke attribute.
   *
   * @param stroke value
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
