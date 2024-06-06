package se.alipsa.groovy.svg

class Set extends Animation<Set> {

  static final String NAME='set'

  Set(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  Set attributeName(String value) {
    addAttribute('attributeName', value)
  }

  String getAttributeName() {
    getAttribute('attributeName')
  }

  Set to(String value) {
    addAttribute('to', value)
  }

  String getTo() {
    getAttribute('to')
  }

  Set begin(String value) {
    addAttribute('begin', value)
  }

  String getBegin() {
    getAttribute('begin')
  }

  Set dur(String value) {
    addAttribute('dur', value)
  }

  String getDur() {
    getAttribute('dur')
  }
}
