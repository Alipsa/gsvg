package se.alipsa.groovy.svg

import groovy.transform.CompileStatic
import org.dom4j.Element
import org.dom4j.Namespace
import org.dom4j.QName

/**
 * Base class for all SVG elements backed by DOM4J elements.
 *
 * <h2>Constructor Pattern</h2>
 * Each concrete SvgElement subclass provides two types of constructors:
 *
 * <ol>
 * <li><b>Creating constructors</b> - Create new DOM elements and add them to the parent:
 *     <pre>ClassName(SvgElement parent)</pre>
 *     These constructors call {@code super(parent, NAME)} to create a new DOM element.
 * </li>
 * <li><b>Adopting constructors</b> - Adopt existing DOM elements for cloning/copying:
 *     <pre>ClassName(SvgElement parent, Element element)</pre>
 *     These constructors call {@code super(parent, element)} to wrap an existing DOM element.
 *     Used by {@link SvgElementFactory} for pure object-oriented element copying without
 *     XML serialization.
 * </li>
 * </ol>
 *
 * The adopting constructor pattern enables efficient deep copying of SVG element trees
 * while maintaining both the DOM4J structure and the SvgElement object hierarchy.
 */
@CompileStatic
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
   * Creates a SvgElement by adopting an existing DOM4J Element.
   * This constructor is used for cloning/copying elements.
   * The element is detached from its old parent and added to the new parent.
   *
   * @param parent the parent SVG element
   * @param existingElement the DOM4J element to adopt
   */
  protected SvgElement(SvgElement<? extends SvgElement> parent, Element existingElement) {
    this.parent = parent
    Element cloned = existingElement.createCopy()
    this.element = cloned
    parent.element.add(cloned)
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

  Title addTitle(Map attributes) {
    Title t = addTitle()
    attributes.each {
      key, value -> t.addAttribute(String.valueOf(key), value)
    }
    t
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

  Desc addDesc(Map attributes) {
    Desc d = addDesc()
    attributes.each {
      key, value -> d.addAttribute(String.valueOf(key), value)
    }
    d
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

  /**
   * Overrides Groovy's property access to provide automatic fallback to getAttribute().
   * <p>
   * This allows intuitive property-style access to SVG attributes. When accessing a property
   * that doesn't have an explicit getter (e.g., {@code element.values}), this method will
   * automatically try to retrieve it as an attribute using {@code getAttribute('values')}.
   * <p>
   * This maintains backward compatibility with existing explicit getters while providing
   * a more natural API for accessing arbitrary SVG attributes.
   *
   * @param propertyName the name of the property to access
   * @return the property value, or the attribute value if no property exists
   */
  @Override
  Object getProperty(String propertyName) {
    // Check if a real property/getter exists using MetaClass
    def metaProperty = this.metaClass.hasProperty(this, propertyName)
    if (metaProperty != null) {
      return metaProperty.getProperty(this)
    }

    // If no property exists, try to get it as an attribute
    return getAttribute(propertyName)
  }

  /**
   * Overrides Groovy's property assignment to provide automatic fallback to addAttribute().
   * <p>
   * This allows intuitive property-style assignment to SVG attributes. When assigning to a property
   * that doesn't have an explicit setter (e.g., {@code element.values = '0.5'}), this method will
   * automatically set it as an attribute using {@code addAttribute('values', '0.5')}.
   * <p>
   * This maintains backward compatibility with existing explicit setters while providing
   * a more natural API for setting arbitrary SVG attributes.
   * <p>
   * <b>Note:</b> Use with care - typos in property names will silently create incorrect attributes
   * rather than failing at compile time. For critical code, consider using the explicit fluent API
   * methods (e.g., {@code .gradientUnits('value')}) which provide better IDE autocomplete support.
   *
   * @param propertyName the name of the property to set
   * @param newValue the value to assign
   */
  @Override
  void setProperty(String propertyName, Object newValue) {
    // Check if a method exists that matches the property name (fluent API style)
    // This handles methods like x1(String) that return 'this' for chaining
    def metaMethod = this.metaClass.getMetaMethod(propertyName, newValue)
    if (metaMethod != null) {
      // Call the fluent API method
      metaMethod.invoke(this, newValue)
      return
    }

    // Check if a real property/setter exists using MetaClass
    def metaProperty = this.metaClass.hasProperty(this, propertyName)
    if (metaProperty != null) {
      try {
        // Try to use the real property setter
        metaProperty.setProperty(this, newValue)
        return
      } catch (GroovyRuntimeException e) {
        // If it's read-only (fluent API), fall through to addAttribute
        if (!e.message?.contains('read-only')) {
          throw e
        }
      }
    }

    // If no setter exists or it's read-only, set it as an attribute
    addAttribute(propertyName, newValue)
  }

}
