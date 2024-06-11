package se.alipsa.groovy.svg

class Style extends StringContentContainer<Style> {

  static final String NAME='style'

  Style(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  Style type(String value) {
    addAttribute('type', value)
  }

  String getType() {
    getAttribute('type')
  }
}
