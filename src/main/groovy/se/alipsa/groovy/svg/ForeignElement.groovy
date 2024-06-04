package se.alipsa.groovy.svg

class ForeignElement extends SvgElement<ForeignElement> {

  List<ForeignElement> children = []

  ForeignElement(SvgElement parent, String qName) {
    super(parent, qName)
  }

  ForeignElement(SvgElement parent, String qName, String defaultNameSpace) {
    super(parent, qName, defaultNameSpace)
  }

  ForeignElement addAttribute(String name, Object value) {
    element.addAttribute(name, "$value")
    this
  }

  ForeignElement addContent(String content) {
    element.addText(content)
    this
  }

  ForeignElement addElement(String qName) {
    ForeignElement child = new ForeignElement(this, qName)
    children << child
    child
  }
}
