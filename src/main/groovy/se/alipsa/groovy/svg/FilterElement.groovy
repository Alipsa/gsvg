package se.alipsa.groovy.svg

abstract class FilterElement<T extends SvgElement<T>> extends SvgElement<T> {

  FilterElement(SvgElement<? extends SvgElement> parent, String name) {
    super(parent, name)
  }

  T result(String result) {
    addAttribute('result', result)
  }

  String getResult() {
    getAttribute('result')
  }

  T x(Number x) {
    addAttribute('x', String.valueOf(x))
  }

  String getX() {
    getAttribute('x')
  }

  T y(Number y) {
    addAttribute('y', String.valueOf(y))
  }

  String getY() {
    getAttribute('y')
  }

  T width(Number width) {
    addAttribute('width', "$width")
  }

  T width(String width) {
    addAttribute('width', width)
  }

  String getWidth() {
    getAttribute('width')
  }

  T height(Number height) {
    addAttribute('height', "$height")
  }

  T height(String height) {
    addAttribute('height', height)
  }

  String getHeight() {
    getAttribute('height')
  }
}
