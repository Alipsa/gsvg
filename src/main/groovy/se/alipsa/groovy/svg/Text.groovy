package se.alipsa.groovy.svg

class Text implements SvgElement {

  /**The x position of the start of the text. Default is 0 */
  Number x

  /** The y position of the start of the text. Default is 0 */
  Number y

  /** The horizontal shift position for text (from previous text position) */
  Number dx

  /** The vertical shift position for text (from previous text position)*/
  Number dy

  /** The rotation (in degrees) applied to each letter of text */
  Number rotate

  /** The width that the text must fit in */
  Number textLength

  /** How the text should be compressed or stretched to fit the width defined by the textLength attribute */
  Number lengthAdjust

  String transform

  List<Tspan> tspans

  @Override
  String toXml() {
    return null
  }
}
