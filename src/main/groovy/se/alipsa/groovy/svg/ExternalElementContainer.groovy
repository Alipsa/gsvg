package se.alipsa.groovy.svg

import org.dom4j.QName

/**
 * Interface for elements that can create child elements in foreign namespaces.
 */
interface ExternalElementContainer<T> {

  /**
   * Adds element.
   *
   * @param qName the qualified name of the element
   */
  addElement(String qName)

  /**
   * Adds element.
   *
   * @param qName the qualified name of the element
   */
  addElement(QName qName)

  /**
   * Adds element.
   *
   * @param localName the local name of the element
   * @param namespaceUri the namespace URI
   */
  addElement(String localName, String namespaceUri)
}
