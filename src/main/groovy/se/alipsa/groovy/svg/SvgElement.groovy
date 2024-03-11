package se.alipsa.groovy.svg

import org.dom4j.Element

abstract class SvgElement<T extends SvgElement<T>> {

  protected Element element
  SvgElement parent

  String toXml() {
    element.asXML()
  }

  SvgElement(SvgElement<T> parent, String name) {
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
      if ('strokeWidth'.equals(key)) {
        key = 'stroke-width'
      }
      addAttribute(key, it.value)
    }
    this as T
  }

  SvgElement getParent() {
    parent
  }

  <P> P getParent(Class<P> type) {
    return type.cast(parent)
  }

}
