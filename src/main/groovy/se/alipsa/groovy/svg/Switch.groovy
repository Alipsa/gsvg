package se.alipsa.groovy.svg

class Switch extends AbstractElementContainer<Switch> {
  static final String NAME='switch'

  Switch(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  Switch systemLanguage(String value) {
    addAttribute('systemLanguage', value)
  }

  String getSystemLanguage() {
    getAttribute('systemLanguage')
  }

  Switch requiredFeatures(String value) {
    addAttribute('requiredFeatures', value)
  }

  String getRequiredFeatures() {
    getAttribute('requiredFeatures')
  }

  Switch requiredExtensions(String value) {
    addAttribute('requiredExtensions', value)
  }

  String getRequiredExtensions() {
    getAttribute('requiredExtensions')
  }
}
