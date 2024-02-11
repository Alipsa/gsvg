package se.alipsa.groovy.svg

interface SvgElement {

  String toXml()

  default String optionalAttr(String name, Object val) {
    val == null ? '' : "${name}=\"${val}\""
  }
}
