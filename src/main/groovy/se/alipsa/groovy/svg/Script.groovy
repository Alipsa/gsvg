package se.alipsa.groovy.svg

class Script extends SvgElement<Script> {

  static final String NAME='script'

  Script(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  Script addContent(String content) {
    element.addText(content)
    this
  }

  Script addCdataContent(String content) {
    element.addCDATA(content)
    this
  }

  Script replaceContent(String content) {
    element.setText(content)
    this
  }

  String getContent() {
    element.getText()
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
    getAttribute('href')
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
