package se.alipsa.groovy.svg

import org.dom4j.Namespace
import org.dom4j.QName

class Metadata extends SvgElement<Metadata> implements ExternalElementContainer<Metadata> {

  static final String NAME='metadata'

  Metadata(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  MetadataElement addElement(String qName) {
    add(new MetadataElement(this, qName))
  }

  MetadataElement addElement(String localName, String prefix, String uri) {
    Namespace ns = new Namespace(prefix, uri)
    QName qn = new QName(localName, ns)
    addElement(qn)
  }

  MetadataElement addElement(QName qName) {
    add(new MetadataElement(this, qName))
  }

  List<SvgElement> getChildren() {
    children
  }
}
