package se.alipsa.groovy.svg

class Title extends SvgElement<Title> {

  static final String NAME = 'title'

  Title(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  Title addContent(String value) {
    element.addText(value)
    this
  }

  Title replaceContent(String content) {
    element.setText(content)
    this
  }

  String getContent() {
    element.getText()
  }
}
