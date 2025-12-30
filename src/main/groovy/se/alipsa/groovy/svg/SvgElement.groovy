package se.alipsa.groovy.svg

import org.dom4j.Element
import org.dom4j.Namespace
import org.dom4j.QName

/**
 * Base class for all SVG elements backed by DOM4J elements.
 */
abstract class SvgElement<T extends SvgElement<T>> implements ElementContainer {

  Namespace xlinkNs = new Namespace('xlink', 'http://www.w3.org/1999/xlink')
  Element element
  SvgElement<? extends SvgElement> parent

  Desc desc
  Title title

  /**
   * Serializes this element and its children as XML.
   *
   * @return the result
   */
  String toXml() {
    element.asXML()
  }

  /**
   * Creates a SvgElement.
   *
   * @param parent the parent SVG element
   * @param name the name of the element
   */
  SvgElement(SvgElement<? extends SvgElement> parent, String name) {
    this.parent = parent
    element = parent.element.addElement(name)
  }

  /**
   * Creates a SvgElement.
   *
   * @param parent the parent SVG element
   * @param name the name of the element
   * @param defaultNameSpace the default namespace
   */
  SvgElement(SvgElement<? extends SvgElement> parent, String name, String defaultNameSpace) {
    this.parent = parent
    element = parent.element.addElement(name, defaultNameSpace)
  }

  /**
   * Creates a SvgElement.
   *
   * @param parent the parent SVG element
   * @param qName the qualified name of the element
   */
  SvgElement(SvgElement<? extends SvgElement> parent, QName qName) {
    this.parent = parent
    element = parent.element.addElement(qName)
  }

  /**
   * Special constructor for Svg as it does not have a parent
   * Use this only to create Svg elements!
   */
  SvgElement(Element element) {
    this.element = element
  }

  /**
   * Adds a namespace declaration to this element.
   *
   * @param prefix the namespace prefix
   * @param uri the URI
   * @return this element for chaining
   */
  T addNamespace(String prefix, String uri) {
    element.addNamespace(prefix, uri)
    this as T
  }

  /**
   * Returns the value of the requested attribute.
   *
   * @param name the name of the element
   * @return the result
   */
  String getAttribute(String name) {
    if(name.contains(':')) {
      getAttribute(getQName(name))
    } else {
      element.attributeValue(name)
    }
  }

  /**
   * Returns the value of the requested attribute.
   *
   * @param name the name of the element
   * @return the result
   */
  String getAttribute(QName name) {
    element.attributeValue(name)
  }

  /**
   * Returns the value of the requested attribute.
   *
   * @param nsPrefix the namespace prefix
   * @param localName the local name of the element
   * @return the result
   */
  String getAttribute(String nsPrefix, String localName) {
    getAttribute(getQName(nsPrefix, localName))
  }

  /**
   * Adds an attribute to this element.
   *
   * @param name the name of the element
   * @param value the value
   * @return this element for chaining
   */
  T addAttribute(String name, Object value) {
    if (name.contains(':')) {
      addAttribute(getQName(name), value)
    } else {
      element.addAttribute(name, "$value")
    }
    this as T
  }

  /**
   * Adds an attribute to this element.
   *
   * @param prefix the namespace prefix
   * @param localName the local name of the element
   * @param value the value
   * @return this element for chaining
   */
  T addAttribute(String prefix, String localName, Object value) {
    addAttribute(getQName(prefix, localName), value)
  }

  /**
   * Adds an attribute to this element.
   *
   * @param qname the qualified name
   * @param value the value
   * @return this element for chaining
   */
  T addAttribute(QName qname, Object value) {
    element.addAttribute(qname, "$value")
    this as T
  }

  /**
   * Adds multiple attributes to this element.
   *
   * @param attributes the attributes
   * @return this element for chaining
   */
  T addAttributes(Map<String, Object> attributes) {
    attributes.each {
      String key = it.key
      if ('strokeWidth' == key) {
        key = 'stroke-width'
      }
      addAttribute(key, it.value)
    }
    this as T
  }

  /**
   * Updates an existing attribute on this element.
   *
   * @param attribute the attribute
   * @param value the value
   * @return this element for chaining
   */
  T changeAttribute(String attribute, Object value) {
    element.attribute(attribute)?.setValue("$value")
    this as T
  }

  /**
   * Removes an attribute from this element.
   *
   * @param attribute the attribute
   * @return this element for chaining
   */
  T removeAttribute(String attribute) {
    def attr = element.attribute(attribute)
    if (attr != null) {
      element.remove(attr)
    }
    this as T
  }

  /**
   * Sets the style attribute.
   *
   * @param style the style
   * @return this element for chaining
   */
  T style(String style) {
    addAttribute('style', style)
  }

  /**
   * Sets the style class attribute.
   *
   * @param styleClass the CSS style class
   * @return this element for chaining
   */
  T styleClass(String styleClass) {
    addAttribute('class', styleClass)
  }

  /**
   * Sets the id attribute.
   *
   * @param id the unique identifier
   * @return this element for chaining
   */
  T id(String id) {
    addAttribute('id', id)
  }

  /**
   * Returns the id value.
   *
   * @return the id value
   */
  String getId() {
    getAttribute('id')
  }

  /**
   * Sets the filter attribute.
   *
   * @param filter the filter
   * @return this element for chaining
   */
  T filter(String filter) {
    addAttribute('filter', filter)
  }

  // TODO: Intellij does not recognize the type
  /**
   * Returns the parent value.
   *
   * @return the parent value
   */
  <P extends SvgElement<P>> P getParent() {
    Class<P> clazz = parent.getClass() as Class<P>
    getParent(clazz)
  }

  /**
   * Get parent.
   *
   * @param type the type
   * @return the result
   */
  <P extends SvgElement<P>> P getParent(Class<P> type) {
    return type.cast(parent)
  }

  /**
   * Returns the name value.
   *
   * @return the name value
   */
  String getName() {
    element.getName()
  }

  /**
   * Returns the xlink ns value.
   *
   * @return the xlink ns value
   */
  Namespace getXlinkNs() {
    xlinkNs
  }

  /**
   * Creates and adds a new Title child element.
   *
   * @return the created element
   */
  Title addTitle() {
    title = new Title(this)
  }

  /**
   * Creates and adds a new Title child element.
   *
   * @param content the content
   * @return the created element
   */
  Title addTitle(String content) {
    title = new Title(this).addContent(content)
  }

  /**
   * Returns the title value.
   *
   * @return the title value
   */
  Title getTitle() {
    title
  }

  /**
   * Creates and adds a new Desc child element.
   *
   * @return the created element
   */
  Desc addDesc() {
    desc = new Desc(this)
  }

  /**
   * Creates and adds a new Desc child element.
   *
   * @param content the content
   * @return the created element
   */
  Desc addDesc(String content) {
    desc = new Desc(this).addContent(content)
  }

  /**
   * Returns the desc value.
   *
   * @return the desc value
   */
  Desc getDesc() {
    desc
  }

  /**
   * Builds an xlink-qualified name for the provided local name.
   *
   * @param localName the local name of the element
   * @return the result
   */
  QName xlink(String localName) {
    if (localName == null || localName.isBlank()) {
      throw new IllegalArgumentException("Local name must not be blank for xlink")
    }
    new QName(localName, xlinkNs)
  }

  /**
   * Resolves a QName for the provided name.
   *
   * @param name the name of the element
   * @return the result
   */
  QName getQName(String name) {
    int idx = name.indexOf(':')
    if (idx < 0) {
      throw new IllegalArgumentException("Name '$name' must contain a namespace prefix")
    }
    String prefix = name.substring(0, idx)
    String localName = name.substring(idx + 1)
    if (localName.isBlank()) {
      throw new IllegalArgumentException("Local name must not be blank in '$name'")
    }
    getQName(prefix, localName)
  }

  /**
   * Resolves a QName for the provided name.
   *
   * @param prefix the namespace prefix
   * @param localName the local name of the element
   * @return the result
   */
  QName getQName(String prefix, String localName) {
    Namespace ns = element.getNamespaceForPrefix(prefix)
    if (ns == null) {
      throw new IllegalArgumentException("No namespace bound for prefix '$prefix' on element '${element.getName()}'")
    }
    if (localName == null || localName.isBlank()) {
      throw new IllegalArgumentException("Local name must not be blank for prefix '$prefix'")
    }
    new QName(localName, ns)
  }

}
