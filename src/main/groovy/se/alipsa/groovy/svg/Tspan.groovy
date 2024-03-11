package se.alipsa.groovy.svg

/**
 * The Tspan element is used to mark up parts of a text (just like the HTML <span> element).
 * It must be a child of a <text> element or another <tspan> element.
 * <tspan fill="$fill" stroke="$stroke">$content</tspan>
 */
class Tspan extends SvgElement<Tspan> {

  static final String NAME='tspan'

  Tspan(SvgElement parent) {
    super(parent, NAME)
  }

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
  /** The horizontal shift position for text (from previous text position) */
  Tspan dx(Number... dx) {
    addAttribute('dx', String.join(',', dx.collect {it as String}))
  }

  /** The vertical shift position for text (from previous text position)*/
  Tspan dy(Number... dy) {
    addAttribute('dy', String.join(',', dy.collect {it as String}))
  }

  /** The rotation (in degrees) applied to each letter of text */
  Tspan rotate(Number rotate) {
    addAttribute('rotate', "$rotate")
  }

  /** The width that the text must fit in */
  Tspan textLength(Number textLength) {
    addAttribute('textLength', "$textLength")
  }

  Tspan fill(String fill) {
    addAttribute('fill', fill)
  }

  Tspan stroke(String stroke) {
    addAttribute('stroke', stroke)
  }

  Tspan addContent(String text) {
    element.setText(element.getText() + text)
    this
  }
}
