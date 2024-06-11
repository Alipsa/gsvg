package se.alipsa.groovy.svg

import org.dom4j.QName

class MetadataElement extends StringContentContainer<MetadataElement> implements ExternalElementContainer<MetadataElement> {

  MetadataElement(SvgElement parent, String qName) {
    super(parent, qName)
  }

  MetadataElement(SvgElement parent, QName qName) {
    super(parent, qName)
  }

  MetadataElement addElement(String qName) {
   add(new MetadataElement(this, qName))
  }

  MetadataElement addElement(QName qName) {
    add(new MetadataElement(this, qName))
  }
}
