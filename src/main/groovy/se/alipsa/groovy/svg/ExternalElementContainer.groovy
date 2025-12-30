package se.alipsa.groovy.svg

import org.dom4j.QName

/**
 * Interface for elements that can create child elements in foreign namespaces.
 */
interface ExternalElementContainer<T> {

  /**
   * Adds element.
   *
   * @param qName value
   */
  addElement(String qName)

  /**
   * Adds element.
   *
   * @param qName value
   */
  addElement(QName qName)

  /**
   * Adds element.
   *
   * @param localName value
   * @param namespaceUri value
   */
  addElement(String localName, String namespaceUri)
}
