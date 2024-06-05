package se.alipsa.groovy.svg

class Style extends SvgElement<Style> {

  static final String NAME='style'

  Style(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  Style addContent(String content) {
    element.addText(content)
    this
  }

  Style replaceContent(String content) {
    element.setText(content)
    this
  }

  String getContent() {
    element.getText()
  }

  Style type(String value) {
    addAttribute('type', value)
  }

  String getType() {
    getAttribute('type')
  }
}
