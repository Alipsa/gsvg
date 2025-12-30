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
   * @param parent the parent SVG element
   */
  Metadata(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
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
   * @param localName the local name of the element
   * @param prefix the namespace prefix
   * @param uri the URI
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
   * @param localName the local name of the element
   * @param namespaceUri the namespace URI
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
   * @param qName the qualified name of the element
   * @return the created element
   */
  MetadataElement addElement(QName qName) {
    add(new MetadataElement(this, qName))
  }

  /**
   * Returns the children value.
   *
   * @return the children value
   */
  List<SvgElement> getChildren() {
    children
  }
}
