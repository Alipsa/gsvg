package se.alipsa.groovy.svg

class Symbol extends AbstractElementContainer<Symbol> {

  static final String NAME='symbol'

  Symbol(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  Symbol width(Number width) {
    addAttribute('width', width)
  }

  Symbol width(String width) {
    addAttribute('width', width)
  }

  String getWidth() {
    getAttribute('width')
  }

  Symbol height(Number height) {
    addAttribute('height', height)
  }

  Symbol height(String height) {
    addAttribute('height', height)
  }

  String getHeight() {
    getAttribute('height')
  }

  Symbol x(Number x) {
    addAttribute('x', x)
  }

  Symbol x(String x) {
    addAttribute('x', x)
  }

  String getX() {
    getAttribute('x')
  }

  Symbol y(Number y) {
    addAttribute('y', y)
  }

  Symbol y(String y) {
    addAttribute('y', y)
  }

  String getY() {
    getAttribute('y')
  }

  Symbol preserveAspectRatio(String value) {
    addAttribute('preserveAspectRatio', value)
  }

  String getPreserveAspectRatio() {
    getAttribute('preserveAspectRatio')
  }

  Symbol refX(Number refX) {
    addAttribute('refX', refX)
  }

  String getRefX() {
    getAttribute('refX')
  }

  Symbol refY(Number refY) {
    addAttribute('refY', refY)
  }

  String getRefY() {
    getAttribute('refY')
  }

  Symbol viewBox(String params) {
    addAttribute('viewBox', params)
  }

  String getViewBox() {
    getAttribute('viewBox')
  }
}
