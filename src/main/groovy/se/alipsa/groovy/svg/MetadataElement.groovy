package se.alipsa.groovy.svg

import org.dom4j.Namespace
import org.dom4j.QName

/**
 * Represents metadata elements in non-SVG namespaces within metadata.
 */
class MetadataElement extends StringContentContainer<MetadataElement> implements ExternalElementContainer<MetadataElement> {

  /**
   * Creates a MetadataElement.
   *
   * @param parent value
   * @param qName value
   */
  MetadataElement(SvgElement parent, String qName) {
    super(parent, qName)
  }

  /**
   * Creates a MetadataElement.
   *
   * @param parent value
   * @param qName value
   */
  MetadataElement(SvgElement parent, QName qName) {
    super(parent, qName)
  }

  /**
   * Creates and adds a new MetadataElement child element.
   *
   * @param qName value
   * @return the created element
   */
  MetadataElement addElement(String qName) {
    add(new MetadataElement(this, qName))
  }

  /**
   * Creates and adds a new MetadataElement child element.
   *
   * @param qName value
   * @return the created element
   */
  MetadataElement addElement(QName qName) {
    add(new MetadataElement(this, qName))
  }

  /**
   * Creates and adds a new MetadataElement child element.
   *
   * @param localName value
   * @param namespaceUri value
   * @return the created element
   */
  MetadataElement addElement(String localName, String namespaceUri) {
    Namespace ns = new Namespace('', namespaceUri)
    add(new MetadataElement(this, new QName(localName, ns)))
  }
}
