package se.alipsa.groovy.svg

/**
 * SVG {@code <textPath>} element that renders text along a path.
 */
class TextPath extends StringContentContainer<TextPath> {

  static final String NAME='textPath'

  /**
   * Creates a TextPath.
   *
   * @param parent the parent SVG element
   */
  TextPath(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   * Sets the href attribute.
   *
   * @param href the hyperlink URL
   * @return this element for chaining
   */
  TextPath href(String href) {
    addAttribute('href', href)
  }

  /**
   * Returns the href value.
   *
   * @return the href value
   */
  String getHref() {
    getAttribute('href') ?: getAttribute(xlink('href'))
  }

  /**
   * Sets the length adjust attribute.
   *
   * @param lengthAdjust the length adjustment method
   * @return this element for chaining
   */
  TextPath lengthAdjust(Number lengthAdjust) {
    addAttribute('lengthAdjust', lengthAdjust)
  }

  /**
   * Sets the length adjust attribute.
   *
   * @param lengthAdjust value
   * @return this element for chaining
   */
  TextPath lengthAdjust(String lengthAdjust) {
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
   * Sets the method attribute.
   *
   * @param value the value
   * @return this element for chaining
   */
  TextPath method(String value) {
    addAttribute('method', value)
  }

  /**
   * Returns the method value.
   *
   * @return the method value
   */
  String getMethod() {
    getAttribute('method')
  }

  /**
   * Sets the path attribute.
   *
   * @param value the value
   * @return this element for chaining
   */
  TextPath path(String value) {
    addAttribute('path', value)
  }

  /**
   * Returns the path value.
   *
   * @return the path value
   */
  String getPath() {
    getAttribute('path')
  }

  /**
   * Sets the side attribute.
   *
   * @param value the value
   * @return this element for chaining
   */
  TextPath side(String value) {
    addAttribute('side', value)
  }

  /**
   * Returns the side value.
   *
   * @return the side value
   */
  String getSide() {
    getAttribute('side')
  }

  /**
   * Sets the spacing attribute.
   *
   * @param value the value
   * @return this element for chaining
   */
  TextPath spacing(String value) {
    addAttribute('spacing', value)
  }

  /**
   * Returns the spacing value.
   *
   * @return the spacing value
   */
  String getSpacing() {
    getAttribute('spacing')
  }

  /**
   * Sets the start offset attribute.
   *
   * @param value the value
   * @return this element for chaining
   */
  TextPath startOffset(String value) {
    addAttribute('startOffset', value)
  }

  /**
   * Sets the start offset attribute.
   *
   * @param value the value
   * @return this element for chaining
   */
  TextPath startOffset(Number value) {
    addAttribute('startOffset', value)
  }

  /**
   * Returns the start offset value.
   *
   * @return the start offset value
   */
  String getStartOffset() {
    getAttribute('startOffset')
  }

  /**
   * Sets the text length attribute.
   *
   * @param textLength the text length
   * @return this element for chaining
   */
  TextPath textLength(Number textLength) {
    addAttribute('textLength', textLength)
  }

  /**
   * Sets the text length attribute.
   *
   * @param textLength value
   * @return this element for chaining
   */
  TextPath textLength(String textLength) {
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
   * Creates and adds a new Animate child element.
   *
   * @return the created element
   */
  Animate addAnimate() {
    add(new Animate(this))
  }

  /**
   * Creates and adds a new Set child element.
   *
   * @return the created element
   */
  Set addSet() {
    add(new Set(this))
  }

  /**
   * Creates and adds a new A child element.
   *
   * @return the created element
   */
  A addA() {
    add(new A(this))
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
   * @param content the content
   * @return the created element
   */
  Tspan addTspan(String content) {
    add(new Tspan(this, content))
  }

}
