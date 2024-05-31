package se.alipsa.groovy.svg

class Desc extends SvgElement<Desc> {

  static final String NAME = 'desc'

  Desc(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  Desc addContent(String value) {
    element.addText(value)
    this
  }

  Desc replaceContent(String content) {
    element.setText(content)
    this
  }

  String getContent() {
    element.getText()
  }
}
