package se.alipsa.groovy.svg

import org.dom4j.Namespace
import org.dom4j.QName

/**
 * SVG {@code <foreignObject>} element that embeds foreign XML content such as HTML.
 */
class ForeignObject extends StringContentContainer<ForeignObject> implements Animatable<ForeignObject>, ExternalElementContainer<ForeignObject>  {

  static final String NAME='foreignObject'

  /**
   * Creates a ForeignObject.
   *
   * @param parent the parent SVG element
   */
  ForeignObject(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   * Sets the width attribute.
   *
   * @param width the width
   * @return this element for chaining
   */
  ForeignObject width(Number width) {
    addAttribute('width', width)
  }

  /**
   * Sets the width attribute.
   *
   * @param width the width
   * @return this element for chaining
   */
  ForeignObject width(String width) {
    addAttribute('width', width)
  }

  /**
   * Returns the width value.
   *
   * @return the width value
   */
  String getWidth() {
    getAttribute('width')
  }

  /**
   * Sets the height attribute.
   *
   * @param height the height
   * @return this element for chaining
   */
  ForeignObject height(Number height) {
    addAttribute('height', height)
  }

  /**
   * Sets the height attribute.
   *
   * @param height the height
   * @return this element for chaining
   */
  ForeignObject height(String height) {
    addAttribute('height', height)
  }

  /**
   * Returns the height value.
   *
   * @return the height value
   */
  String getHeight() {
    getAttribute('height')
  }

  /**
   * Sets the x attribute.
   *
   * @param x the x-coordinate
   * @return this element for chaining
   */
  ForeignObject x(Number x) {
    addAttribute('x', x)
  }

  /**
   * Sets the x attribute.
   *
   * @param x value
   * @return this element for chaining
   */
  ForeignObject x(String x) {
    addAttribute('x', x)
  }
  /**
   * Returns the x value.
   *
   * @return the x value
   */
  String getX() {
    getAttribute('x')
  }

  /**
   * Sets the y attribute.
   *
   * @param y the y-coordinate
   * @return this element for chaining
   */
  ForeignObject y(Number y) {
    addAttribute('y', y)
  }

  /**
   * Sets the y attribute.
   *
   * @param y the y-coordinate
   * @return this element for chaining
   */
  ForeignObject y(String y) {
    addAttribute('y', y)
  }

  /**
   * Returns the y value.
   *
   * @return the y value
   */
  String getY() {
    getAttribute('y')
  }

  /**
   * Creates and adds a new ForeignElement child element.
   *
   * @param qName the qualified name of the element
   * @return the created element
   */
  ForeignElement addElement(String qName) {
    add(new ForeignElement(this, qName, "http://www.w3.org/1999/xhtml"))
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
