package se.alipsa.groovy.svg

class TextPath extends StringContentContainer<TextPath> {

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
    add(new Animate(this))
  }

  Set addSet() {
    add(new Set(this))
  }

  A addA() {
    add(new A(this))
  }

  Tspan addTspan() {
    add(new Tspan(this))
  }

  Tspan addTspan(String content) {
    add(new Tspan(this, content))
  }

}
