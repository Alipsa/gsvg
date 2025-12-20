package se.alipsa.groovy.svg

import org.dom4j.Element
import org.dom4j.Namespace
import org.dom4j.QName

abstract class SvgElement<T extends SvgElement<T>> implements ElementContainer {

  Namespace xlinkNs = new Namespace('xlink', 'http://www.w3.org/1999/xlink')
  Element element
  SvgElement<? extends SvgElement> parent

  Desc desc
  Title title

  String toXml() {
    element.asXML()
  }

  SvgElement(SvgElement<? extends SvgElement> parent, String name) {
    this.parent = parent
    element = parent.element.addElement(name)
  }

  SvgElement(SvgElement<? extends SvgElement> parent, String name, String defaultNameSpace) {
    this.parent = parent
    element = parent.element.addElement(name, defaultNameSpace)
  }

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

  T addNamespace(String prefix, String uri) {
    element.addNamespace(prefix, uri)
    this as T
  }

  String getAttribute(String name) {
    if(name.contains(':')) {
      getAttribute(getQName(name))
    } else {
      element.attributeValue(name)
    }
  }

  String getAttribute(QName name) {
    element.attributeValue(name)
  }

  String getAttribute(String nsPrefix, String localName) {
    getAttribute(getQName(nsPrefix, localName))
  }

  T addAttribute(String name, Object value) {
    if (name.contains(':')) {
      addAttribute(getQName(name), value)
    } else {
      element.addAttribute(name, "$value")
    }
    this as T
  }

  T addAttribute(String prefix, String localName, Object value) {
    addAttribute(getQName(prefix, localName), value)
  }

  T addAttribute(QName qname, Object value) {
    element.addAttribute(qname, "$value")
    this as T
  }

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

  T changeAttribute(String attribute, Object value) {
    element.attribute(attribute)?.setValue("$value")
    this as T
  }

  T removeAttribute(String attribute) {
    def attr = element.attribute(attribute)
    if (attr != null) {
      element.remove(attr)
    }
    this as T
  }

  T style(String style) {
    addAttribute('style', style)
  }

  T styleClass(String styleClass) {
    addAttribute('class', styleClass)
  }

  T id(String id) {
    addAttribute('id', id)
  }

  String getId() {
    getAttribute('id')
  }

  T filter(String filter) {
    addAttribute('filter', filter)
  }

  // TODO: Intellij does not recognize the type
  <P extends SvgElement<P>> P getParent() {
    Class<P> clazz = parent.getClass() as Class<P>
    getParent(clazz)
  }

  <P extends SvgElement<P>> P getParent(Class<P> type) {
    return type.cast(parent)
  }

  String getName() {
    element.getName()
  }

  Namespace getXlinkNs() {
    xlinkNs
  }

  Title addTitle() {
    title = new Title(this)
  }

  Title addTitle(String content) {
    title = new Title(this).addContent(content)
  }

  Title getTitle() {
    title
  }

  Desc addDesc() {
    desc = new Desc(this)
  }

  Desc addDesc(String content) {
    desc = new Desc(this).addContent(content)
  }

  Desc getDesc() {
    desc
  }

  QName xlink(String localName) {
    if (localName == null || localName.isBlank()) {
      throw new IllegalArgumentException("Local name must not be blank for xlink")
    }
    new QName(localName, xlinkNs)
  }

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
