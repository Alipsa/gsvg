package se.alipsa.groovy.svg

import org.dom4j.QName

class MetadataElement extends SvgElement<MetadataElement> {

  List<MetadataElement> children = []

  MetadataElement(SvgElement parent, String qName) {
    super(parent, qName)
  }

  MetadataElement(SvgElement parent, QName qName) {
    super(parent, qName)
  }

  MetadataElement addContent(String content) {
    element.addText(content)
    this
  }

  MetadataElement addElement(String qName) {
    MetadataElement child = new MetadataElement(this, qName)
    children << child
    child
  }

  MetadataElement addElement(QName qName) {
    MetadataElement child = new MetadataElement(this, qName)
    children << child
    child
  }

  SvgElement getAt(Integer index) {
    children[index]
  }

  List<SvgElement> getAt(String name) {
    children.stream().filter (e -> e.name == name).findAll()
  }
}
