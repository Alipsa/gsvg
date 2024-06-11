package se.alipsa.groovy.svg

class Title extends StringContentContainer<Title> {

  static final String NAME = 'title'

  Title(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

}
