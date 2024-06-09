package se.alipsa.groovy.svg

class TextPath extends SvgElement<TextPath> {

  static final String NAME='textPath'

  TextPath(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  TextPath href(String href) {
    addAttribute('href', href)
  }

  String getHref() {
    getAttribute('href')
  }

  TextPath lengthAdjust(Number lengthAdjust) {
    addAttribute('lengthAdjust', lengthAdjust)
  }

  String getLengthAdjust() {
    getAttribute('lengthAdjust')
  }

  TextPath method(String value) {
    addAttribute('method', value)
  }

  String getMethod() {
    getAttribute('method')
  }

  TextPath path(String value) {
    addAttribute('path', value)
  }

  String getPath() {
    getAttribute('path')
  }

  TextPath side(String value) {
    addAttribute('side', value)
  }

  String getSide() {
    getAttribute('side')
  }

  TextPath spacing(String value) {
    addAttribute('spacing', value)
  }

  String getSpacing() {
    getAttribute('spacing')
  }

  TextPath startOffset(String value) {
    addAttribute('startOffset', value)
  }

  TextPath startOffset(Number value) {
    addAttribute('startOffset', value)
  }

  String getStartOffset() {
    getAttribute('startOffset')
  }

  TextPath textLength(Number textLength) {
    addAttribute('textLength', textLength)
  }

  String getTextLength() {
    getAttribute('textLength')
  }

  Animate addAnimate() {
    Animate animate = new Animate(this)
    children << animate
    animate
  }

  Set addSet() {
    new Set(this)
  }

  A addA() {
    new A(this)
  }

  TextPath addContent(String content) {
    element.addText(content)
    this
  }

  TextPath replaceContent(String content) {
    element.setText(content)
    this
  }

  String getContent() {
    element.getText()
  }

  Tspan addTspan() {
    new Tspan(this)
  }

  Tspan addTspan(String content) {
    new Tspan(this, content)
  }

}
