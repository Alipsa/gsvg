package se.alipsa.groovy.svg

import groovy.transform.CompileStatic
import org.dom4j.Namespace
import org.dom4j.QName

/**
 * Represents metadata elements in non-SVG namespaces within metadata.
 */
@CompileStatic
class MetadataElement extends StringContentContainer<MetadataElement> implements ExternalElementContainer<MetadataElement> {

  /**
   * Creates a MetadataElement.
   *
   * @param parent the parent SVG element
   * @param qName the qualified name of the element
   */
  MetadataElement(SvgElement parent, String qName) {
    super(parent, qName)
  }

  /**
   * Creates a MetadataElement.
   *
   * @param parent the parent SVG element
   * @param qName the qualified name of the element
   */
  MetadataElement(SvgElement parent, QName qName) {
    super(parent, qName)
  }

  /**
   * Creates and adds a new MetadataElement child element.
   *
   * @param qName the qualified name of the element
   * @return the created element
   */
  MetadataElement addElement(String qName) {
    add(new MetadataElement(this, qName))
  }

  /**
   * Creates and adds a new MetadataElement child element.
   *
   * @param qName the qualified name of the element
   * @return the created element
   */
  MetadataElement addElement(QName qName) {
    add(new MetadataElement(this, qName))
  }

  /**
   * Creates and adds a new MetadataElement child element.
   *
   * @param localName the local name of the element
   * @param namespaceUri the namespace URI
   * @return the created element
   */
  MetadataElement addElement(String localName, String namespaceUri) {
    Namespace ns = new Namespace('', namespaceUri)
    add(new MetadataElement(this, new QName(localName, ns)))
  }
}
