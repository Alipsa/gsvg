package se.alipsa.groovy.svg

import org.dom4j.Element

abstract class SvgElement<T extends SvgElement<T>> {

  protected Element element
  SvgElement<? extends SvgElement> parent

  String toXml() {
    element.asXML()
  }

  SvgElement(SvgElement<? extends SvgElement> parent, String name) {
    this.parent = parent
    element = parent.element.addElement(name)
  }

  /**
   * Special constructor for Svg asit does not have a parent
   * Use this only to create Svg elements!
   */
  SvgElement(Element element) {
    this.element = element
  }

  T addAttribute(String name, Object value) {
    element.addAttribute(name, "$value")
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
    element.remove(element.attribute(attribute))
    this as T
  }

  // TODO: this does not return he type, or at least Intellij does not recognize it
  <P extends SvgElement<P>> P getParent() {
    Class<P> clazz = parent.getClass() as Class<P>
    getParent(clazz)
  }

  <P extends SvgElement<P>> P getParent(Class<P> type) {
    return type.cast(parent)
  }

}
