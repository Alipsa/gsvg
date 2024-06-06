package se.alipsa.groovy.svg

import org.dom4j.Namespace
import org.dom4j.QName

class Metadata extends SvgElement<Metadata> {

  static final String NAME='metadata'

  List<MetadataElement> metadataElements = []

  Metadata(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  MetadataElement addElement(String qName) {
    MetadataElement e = new MetadataElement(this, qName)
    metadataElements << e
    e
  }

  MetadataElement addElement(String localName, String prefix, String uri) {
    Namespace ns = new Namespace(prefix, uri)
    QName qn = new QName(localName, ns)
    addElement(qn)
  }

  MetadataElement addElement(QName qName) {
    MetadataElement e = new MetadataElement(this, qName)
    metadataElements << e
    e
  }

  List<MetadataElement>getChildren() {
    metadataElements
  }

  MetadataElement getAt(Integer index) {
    metadataElements[index]
  }

  List<MetadataElement> getAt(String name) {
    metadataElements.stream().filter (e -> e.element.name == name).findAll()
  }
}
