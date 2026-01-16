package se.alipsa.groovy.svg



import groovy.transform.PackageScope
import org.dom4j.Element
import groovy.transform.CompileStatic
import org.dom4j.Namespace
import org.dom4j.QName

/**
 * Represents a foreign namespace element embedded within SVG content.
 */
@CompileStatic
class ForeignElement extends StringContentContainer<ForeignElement> implements ExternalElementContainer<ForeignElement> {

  /**
   * Creates a ForeignElement.
   *
   * @param parent the parent SVG element
   * @param qName the qualified name of the element
   */
  ForeignElement(SvgElement parent, String qName) {
    super(parent, qName)
  }

  /**
   * Creates a ForeignElement.
   *
   * @param parent the parent SVG element
   * @param qName the qualified name of the element
   * @param defaultNameSpace the default namespace
   */
  ForeignElement(SvgElement parent, String qName, String defaultNameSpace) {
    super(parent, qName, defaultNameSpace)
  }

  /**
   * Creates a ForeignElement.
   *
   * @param parent the parent SVG element
   * @param qName the qualified name of the element
   */
  ForeignElement(SvgElement parent, QName qName) {
    super(parent, qName)
  }

  @PackageScope
  ForeignElement(SvgElement parent, Element element) {
    super(parent, element)
  }


  /**
   * Creates and adds a new ForeignElement child element.
   *
   * @param qName the qualified name of the element
   * @return the created element
   */
  ForeignElement addElement(String qName) {
    add(new ForeignElement(this, qName))
  }

  /**
   * Creates and adds a new ForeignElement child element.
   *
   * @param qName the qualified name of the element
   * @return the created element
   */
  ForeignElement addElement(QName qName) {
    add(new ForeignElement(this, qName))
  }

  /**
   * Creates and adds a new ForeignElement child element.
   *
   * @param localName the local name of the element
   * @param namespaceUri the namespace URI
   * @return the created element
   */
  ForeignElement addElement(String localName, String namespaceUri) {
    Namespace ns = new Namespace('', namespaceUri)
    add(new ForeignElement(this, new QName(localName, ns)))
  }
}