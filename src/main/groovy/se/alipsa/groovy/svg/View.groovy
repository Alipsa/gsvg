package se.alipsa.groovy.svg

class View extends SvgElement<View> {

  static final String NAME = 'view'

  View(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  View viewBox(String value) {
    addAttribute('viewBox', value)
  }

  String getViewBox() {
    getAttribute('viewBox')
  }

  View preserveAspectRatio(String value) {
    addAttribute('preserveAspectRatio', value)
  }

  String getPreserveAspectRatio() {
    getAttribute('preserveAspectRatio')
  }
}
