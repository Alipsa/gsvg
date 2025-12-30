package se.alipsa.groovy.svg

import org.dom4j.Namespace
import org.dom4j.QName

/**
 * Represents a foreign namespace element embedded within SVG content.
 */
class ForeignElement extends StringContentContainer<ForeignElement> implements ExternalElementContainer<ForeignElement> {

  /**
   * Creates a ForeignElement.
   *
   * @param parent value
   * @param qName value
   */
  ForeignElement(SvgElement parent, String qName) {
    super(parent, qName)
  }

  /**
   * Creates a ForeignElement.
   *
   * @param parent value
   * @param qName value
   * @param defaultNameSpace value
   */
  ForeignElement(SvgElement parent, String qName, String defaultNameSpace) {
    super(parent, qName, defaultNameSpace)
  }

  /**
   * Creates a ForeignElement.
   *
   * @param parent value
   * @param qName value
   */
  ForeignElement(SvgElement parent, QName qName) {
    super(parent, qName)
  }

  /**
   * Creates and adds a new ForeignElement child element.
   *
   * @param qName value
   * @return the created element
   */
  ForeignElement addElement(String qName) {
    add(new ForeignElement(this, qName))
  }

  /**
   * Creates and adds a new ForeignElement child element.
   *
   * @param qName value
   * @return the created element
   */
  ForeignElement addElement(QName qName) {
    add(new ForeignElement(this, qName))
  }

  /**
   * Creates and adds a new ForeignElement child element.
   *
   * @param localName value
   * @param namespaceUri value
   * @return the created element
   */
  ForeignElement addElement(String localName, String namespaceUri) {
    Namespace ns = new Namespace('', namespaceUri)
    add(new ForeignElement(this, new QName(localName, ns)))
  }
}
