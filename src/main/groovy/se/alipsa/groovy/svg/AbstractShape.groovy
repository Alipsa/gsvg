package se.alipsa.groovy.svg

class AbstractShape<T extends SvgElement<T>> extends SvgElement<T> implements Animatable<T>{

  AbstractShape(SvgElement<? extends SvgElement> parent, String name) {
    super(parent, name)
  }

  T mask(String ref) {
    addAttribute('mask', ref)
  }

  String getMask() {
    getAttribute('mask')
  }

  T onClick(String value) {
    addAttribute('onclick', value)
  }

  T fillRule(String value) {
    addAttribute('fill-rule', value)
  }

  String getFillRule() {
    getAttribute('fill-rule')
  }

  T strokeLinejoin(String value) {
    addAttribute('stroke-linejoin', value)
  }

  String getStrokeLinejoin() {
    getAttribute('stroke-linejoin')
  }

  T strokeLinecap(String value) {
    addAttribute('stroke-linecap', value)
  }

  String getStrokeLinecap() {
    getAttribute('stroke-linecap')
  }
}
