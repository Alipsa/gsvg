package se.alipsa.groovy.svg

class ForeignElement extends StringContentContainer<ForeignElement> implements ExternalElementContainer<ForeignElement> {

  ForeignElement(SvgElement parent, String qName) {
    super(parent, qName)
  }

  ForeignElement(SvgElement parent, String qName, String defaultNameSpace) {
    super(parent, qName, defaultNameSpace)
  }

  ForeignElement addElement(String qName) {
    add(new ForeignElement(this, qName))
  }
}
