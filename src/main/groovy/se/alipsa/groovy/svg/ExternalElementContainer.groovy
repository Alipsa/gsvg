package se.alipsa.groovy.svg

import org.dom4j.QName

interface ExternalElementContainer<T> {

  addElement(String qName)

  addElement(QName qName)

  addElement(String localName, String namespaceUri)
}
