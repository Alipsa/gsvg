package se.alipsa.groovy.svg

/**
 * <text x="$x" y="$y" fill="$fill" font-size="$fontSize">$sb</text>
 */
class Text extends SvgElement<Text> implements Animatable<Text> {

  static final String NAME='text'

  Text(SvgElement parent){
    super(parent, NAME)
  }

  Text(SvgElement parent, String text) {
    this(parent)
    element.addText(text)
  }

  Text fill(String fill) {
    addAttribute('fill', fill)
  }

  /**The x position of the start of the text. Default is 0 */
  Text x(Number x) {
    addAttribute('x', String.valueOf(x))
  }

  /** The y position of the start of the text. Default is 0 */
  Text y(Number y) {
    addAttribute('y', String.valueOf(y))
  }

  Text addContent(String content) {
    element.addText(content)
    this
  }

  Text replaceContent(String content) {
    element.setText(content)
    this
  }

  String getContent() {
    element.getText()
  }

  Tspan addTspan() {
    return new Tspan(this)
  }

  Tspan addTspan(String content) {
    return new Tspan(this, content)
  }

  TextPath addTextPath() {
    return new TextPath(this)
  }

  Text fontFamily(String family) {
    addAttribute('font-family', family)
  }

  String getFontFamily() {
    getAttribute('font-family')
  }

  Text fontSize(Number size) {
    addAttribute('font-size', String.valueOf(size))
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

  String getRotate() {
    getAttribute('rotate')
  }

  Text textAnchor(String anchor) {
    addAttribute('text-anchor', anchor)
  }

  String getTextAnchor() {
    getAttribute('text-anchor')
  }

  /** The width that the text must fit in */
  Text textLength(Number textLength) {
    addAttribute('textLength', textLength)
  }

  String getTextLength() {
    getAttribute('textLength')
  }

  /** How the text should be compressed or stretched to fit the width defined by the textLength attribute */
  Text lengthAdjust(Number lengthAdjust) {
    addAttribute('lengthAdjust', lengthAdjust)
  }

  String getLengthAdjust() {
    getAttribute('lengthAdjust')
  }

  Text transform(String transform) {
    addAttribute('transform', transform)
  }

  String getTransform() {
    getAttribute('transform')
  }
}
