package se.alipsa.groovy.svg

import org.dom4j.Namespace
import org.dom4j.QName

class ForeignElement extends StringContentContainer<ForeignElement> implements ExternalElementContainer<ForeignElement> {

  ForeignElement(SvgElement parent, String qName) {
    super(parent, qName)
  }

  ForeignElement(SvgElement parent, String qName, String defaultNameSpace) {
    super(parent, qName, defaultNameSpace)
  }

  ForeignElement(SvgElement parent, QName qName) {
    super(parent, qName)
  }

  ForeignElement addElement(String qName) {
    add(new ForeignElement(this, qName))
  }

  ForeignElement addElement(QName qName) {
    add(new ForeignElement(this, qName))
  }

  ForeignElement addElement(String localName, String namespaceUri) {
    Namespace ns = new Namespace('', namespaceUri)
    add(new ForeignElement(this, new QName(localName, ns)))
  }
}
