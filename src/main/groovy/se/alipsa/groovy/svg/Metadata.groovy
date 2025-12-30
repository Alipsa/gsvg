package se.alipsa.groovy.svg

import org.dom4j.Namespace
import org.dom4j.QName

/**
 * SVG {@code <metadata>} element that stores metadata for the document.
 */
class Metadata extends SvgElement<Metadata> implements ExternalElementContainer<Metadata> {

  static final String NAME='metadata'

  /**
   * Creates a Metadata.
   *
   * @param parent value
   */
  Metadata(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
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
   * @param localName value
   * @param prefix value
   * @param uri value
   * @return the created element
   */
  MetadataElement addElement(String localName, String prefix, String uri) {
    Namespace ns = new Namespace(prefix, uri)
    QName qn = new QName(localName, ns)
    addElement(qn)
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
    QName qn = new QName(localName, ns)
    addElement(qn)
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

  List<SvgElement> getChildren() {
    children
  }
}
