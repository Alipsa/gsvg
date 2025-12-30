package se.alipsa.groovy.svg

/**
 * SVG {@code <text>} element that renders text content.
 */
class Text extends StringContentContainer<Text> implements Animatable<Text> {

  static final String NAME='text'

  /**
   * Creates a Text.
   *
   * @param parent value
   */
  Text(SvgElement parent){
    super(parent, NAME)
  }

  /**
   * Creates a Text.
   *
   * @param parent value
   * @param text value
   */
  Text(SvgElement parent, String text) {
    this(parent)
    element.addText(text)
  }

  /**
   * Sets the fill attribute.
   *
   * @param fill value
   * @return this element for chaining
   */
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

  /**
   * Creates and adds a new Tspan child element.
   *
   * @return the created element
   */
  Tspan addTspan() {
    add(new Tspan(this))
  }

  /**
   * Creates and adds a new Tspan child element.
   *
   * @param content value
   * @return the created element
   */
  Tspan addTspan(String content) {
    add(new Tspan(this, content))
  }

  /**
   * Creates and adds a new TextPath child element.
   *
   * @return the created element
   */
  TextPath addTextPath() {
    add(new TextPath(this))
  }

  /**
   * Sets the font family attribute.
   *
   * @param family value
   * @return this element for chaining
   */
  Text fontFamily(String family) {
    addAttribute('font-family', family)
  }

  String getFontFamily() {
    getAttribute('font-family')
  }

  /**
   * Sets the font size attribute.
   *
   * @param size value
   * @return this element for chaining
   */
  Text fontSize(Number size) {
    addAttribute('font-size', String.valueOf(size))
  }

  /**
   * Text.
   *
   * @param position value
   * @return the result
   */
  /** The horizontal shift position for text (from previous text position) */
  Text dx(Number dx) {
    addAttribute('dx', "$dx")
  }

  /**
   * Text.
   *
   * @param position value
   * @return the result
   */
  /** The vertical shift position for text (from previous text position)*/
  Text dy(Number dy) {
    addAttribute('dy', "$dy")
  }

  /**
   * Rotation.
   *
   * @param degrees value
   * @return the result
   */
  /** The rotation (in degrees) applied to each letter of text */
  Text rotate(Number rotate) {
    addAttribute('rotate', "$rotate")
  }

  String getRotate() {
    getAttribute('rotate')
  }

  /**
   * Sets the text anchor attribute.
   *
   * @param anchor value
   * @return this element for chaining
   */
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

  /**
   * Sets the transform attribute.
   *
   * @param transform value
   * @return this element for chaining
   */
  Text transform(String transform) {
    addAttribute('transform', transform)
  }

  String getTransform() {
    getAttribute('transform')
  }

  /**
   * Sets the baseline shift attribute.
   *
   * @param value value
   * @return this element for chaining
   */
  Text baselineShift(String value) {
    addAttribute('baseline-shift', value)
    this
  }

  String getBaselineShift() {
    getAttribute('baseline-shift')
  }

  /**
   * Sets the dominant baseline attribute.
   *
   * @param value value
   * @return this element for chaining
   */
  Text dominantBaseline(String value) {
    addAttribute('dominant-baseline', value)
    this
  }

  String getDominantBaseline() {
    getAttribute('dominant-baseline')
  }

  /**
   * Sets the text decoration attribute.
   *
   * @param value value
   * @return this element for chaining
   */
  Text textDecoration(String value) {
    addAttribute('text-decoration', value)
    this
  }

  String getTextDecoration() {
    getAttribute('text-decoration')
  }

  /**
   * Sets the word spacing attribute.
   *
   * @param value value
   * @return this element for chaining
   */
  Text wordSpacing(String value) {
    addAttribute('word-spacing', value)
    this
  }

  String getWordSpacing() {
    getAttribute('word-spacing')
  }

  /**
   * Sets the letter spacing attribute.
   *
   * @param value value
   * @return this element for chaining
   */
  Text letterSpacing(String value) {
    addAttribute('letter-spacing', value)
    this
  }

  String getLetterSpacing() {
    getAttribute('letter-spacing')
  }
}
