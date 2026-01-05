package se.alipsa.groovy.svg

import groovy.transform.CompileStatic

/**
 * SVG {@code <text>} element that renders text content.
 */
@CompileStatic
class Text extends StringContentContainer<Text> implements Animatable<Text> {

  static final String NAME='text'

  /**
   * Creates a Text.
   *
   * @param parent the parent SVG element
   */
  Text(SvgElement parent){
    super(parent, NAME)
  }

  /**
   * Creates a Text.
   *
   * @param parent the parent SVG element
   * @param text the text value to add
   */
  Text(SvgElement parent, String text) {
    this(parent)
    element.addText(text)
  }

  /**
   * Sets the fill attribute.
   *
   * @param fill the fill value
   * @return this element for chaining
   */
  Text fill(String fill) {
    addAttribute('fill', fill)
  }

  /**
   * The x position of the start of the text. Default is 0.
   *
   * @param x the x value
   * @return this element for chaining
   */
  Text x(Number x) {
    addAttribute('x', String.valueOf(x))
  }

  /**
   * The x position of the start of the text. Default is 0.
   *
   * @param x the x value
   * @return this element for chaining
   */
  Text x(String x) {
    addAttribute('x', String.valueOf(x))
  }

  /**
   * The y position of the start of the text. Default is 0.
   *
   * @param y the y position
   * @return this element for chaining
   */
  Text y(Number y) {
    addAttribute('y', String.valueOf(y))
  }

  /**
   * The y position of the start of the text. Default is 0.
   *
   * @param y the y position
   * @return this element for chaining
   */
  Text y(String y) {
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

  Tspan addTspan(Map attributes) {
    Tspan tspan = addTspan()
    attributes.each {
      key, value -> tspan.addAttribute(String.valueOf(key), String.valueOf(value))
    }
    tspan
  }

  /**
   * Creates and adds a new TextPath child element.
   *
   * @return the created element
   */
  TextPath addTextPath() {
    add(new TextPath(this))
  }

  TextPath addTextPath(Map attributes) {
    TextPath tp = addTextPath()
    attributes.each {
      key, value -> tp.addAttribute(String.valueOf(key), String.valueOf(value))
    }
    tp
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

  /**
   * Returns the font family value.
   *
   * @return the font family value
   */
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
   * Sets the font size attribute.
   *
   * @param size value
   * @return this element for chaining
   */
  Text fontSize(String size) {
    addAttribute('font-size', String.valueOf(size))
  }

  /**
   * The horizontal shift position for text (from previous text position).
   *
   * @param dx value
   * @return this element for chaining
   */
  Text dx(Number dx) {
    addAttribute('dx', "$dx")
  }

  /**
   * The horizontal shift position for text (from previous text position).
   *
   * @param dx value
   * @return this element for chaining
   */
  Text dx(String dx) {
    addAttribute('dx', "$dx")
  }

  /**
   * The vertical shift position for text (from previous text position).
   *
   * @param dy value
   * @return this element for chaining
   */
  Text dy(Number dy) {
    addAttribute('dy', "$dy")
  }

  /**
   * The vertical shift position for text (from previous text position).
   *
   * @param dy value
   * @return this element for chaining
   */
  Text dy(String dy) {
    addAttribute('dy', "$dy")
  }

  /**
   * The rotation (in degrees) applied to each letter of text.
   *
   * @param rotate value
   * @return this element for chaining
   */
  Text rotate(Number rotate) {
    addAttribute('rotate', "$rotate")
  }

  /**
   * The rotation (in degrees) applied to each letter of text.
   *
   * @param rotate value
   * @return this element for chaining
   */
  Text rotate(String rotate) {
    addAttribute('rotate', "$rotate")
  }

  /**
   * Returns the rotate value.
   *
   * @return the rotate value
   */
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

  /**
   * Returns the text anchor value.
   *
   * @return the text anchor value
   */
  String getTextAnchor() {
    getAttribute('text-anchor')
  }

  /**
   * The width that the text must fit in.
   *
   * @param textLength value
   * @return this element for chaining
   */
  Text textLength(Number textLength) {
    addAttribute('textLength', textLength)
  }

  /**
   * The width that the text must fit in.
   *
   * @param textLength value
   * @return this element for chaining
   */
  Text textLength(String textLength) {
    addAttribute('textLength', textLength)
  }

  /**
   * Returns the text length value.
   *
   * @return the text length value
   */
  String getTextLength() {
    getAttribute('textLength')
  }

  /**
   * How the text should be compressed or stretched to fit the width defined by the textLength attribute.
   *
   * @param lengthAdjust value e.g. spacing, spacingAndGlyphs
   * @return this element for chaining
   */
  Text lengthAdjust(String lengthAdjust) {
    addAttribute('lengthAdjust', lengthAdjust)
  }
  /**
   * Returns the length adjust value.
   *
   * @return the length adjust value
   */
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

  /**
   * Returns the transform value.
   *
   * @return the transform value
   */
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

  /**
   * Returns the baseline shift value.
   *
   * @return the baseline shift value
   */
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

  /**
   * Returns the dominant baseline value.
   *
   * @return the dominant baseline value
   */
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

  /**
   * Returns the text decoration value.
   *
   * @return the text decoration value
   */
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

  /**
   * Returns the word spacing value.
   *
   * @return the word spacing value
   */
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

  /**
   * Returns the letter spacing value.
   *
   * @return the letter spacing value
   */
  String getLetterSpacing() {
    getAttribute('letter-spacing')
  }
}
