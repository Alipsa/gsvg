package se.alipsa.groovy.svg

import org.dom4j.Element

abstract class SvgElement<T extends SvgElement<T>> {

  protected Element element

  String toXml() {
    element.asXML()
  }

  SvgElement(Element element) {
    this.element = element
  }

  T addAttribute(String name, Object value) {
    element.addAttribute(name, "$value")
    this as T
  }

}
