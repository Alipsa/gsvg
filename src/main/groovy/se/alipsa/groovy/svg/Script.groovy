package se.alipsa.groovy.svg

class Script extends StringContentContainer<Script> {

  static final String NAME='script'

  Script(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  Script type(String value) {
    addAttribute('type', value)
  }

  String getType() {
    getAttribute('type')
  }

  Script href(String href) {
    addAttribute('href', href)
  }

  String getHref() {
    getAttribute('href') ?: getAttribute(xlink('href'))
  }

  Script xlinkHref(String href) {
    addAttribute(xlink('href'), href)
  }

  String getXlinkHref() {
    getAttribute(xlink('href'))
  }

  /**
   *
   * @param value one of "anonymous", "use-credentials", ""
   * @return
   */
  Script crossorigin(String value) {
    addAttribute('crossorigin', value)
  }

  String getCrossorigin() {
    getAttribute('crossorigin')
  }
}
