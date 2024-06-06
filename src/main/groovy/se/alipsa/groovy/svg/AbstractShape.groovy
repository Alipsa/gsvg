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
}
