package se.alipsa.groovy.svg

import org.dom4j.Element

/**
 * The Tspan element is used to mark up parts of a text (just like the HTML <span> element).
 * It must be a child of a <text> element or another <tspan> element.
 * <tspan fill="$fill" stroke="$stroke">$content</tspan>
 */
class Tspan extends SvgElement<Tspan> {

  Tspan(Element parent) {
    super(parent.addElement('tspan'))
  }

  Tspan(Element parent, String text) {
    this(parent)
    addText(text)
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
  Text dx(Number dx) {
    addAttribute('dx', "$dx")
  }

  /** The vertical shift position for text (from previous text position)*/
  Text dy(Number dy) {
    addAttribute('dy', "$dy")
  }

  /** The rotation (in degrees) applied to each letter of text */
  Text rotate(Number rotate) {
    addAttribute('rotate', "$rotate")
  }

  /** The width that the text must fit in */
  Text textLength(Number textLength) {
    addAttribute('textLength', "$textLength")
  }

  Tspan fill(String fill) {
    addAttribute('fill', fill)
  }

  Tspan stroke(String stroke) {
    addAttribute('stroke', stroke)
  }

  Tspan addText(String text) {
    element.setText(text)
    this
  }
}
