package se.alipsa.groovy.svg

class FeImage extends FilterElement<FeImage> {
  static final String NAME = 'feImage'

  FeImage(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  FeImage xlinkHref(String href) {
    addAttribute(xlink('href'), href)
  }

  FeImage href(String href) {
    addAttribute('href', href)
  }

  String getHref() {
    getAttribute('href') ?: getAttribute(xlink('href'))
  }

  String getXlinkHref() {
    getAttribute(xlink('href'))
  }

  @Override
  FeImage addAttribute(String name, Object value) {
    if ('href' == name) {
      element.addAttribute('href', "$value")
      return this
    }
    if ('xlink:href' == name) {
      element.addAttribute(xlink('href'), "$value")
      return this
    }
    super.addAttribute(name, value) as FeImage
  }

  /**
   *
   * @param value one of "anonymous", "use-credentials", ""
   * @return
   */
  FeImage crossorigin(String value) {
    addAttribute('crossorigin', value)
  }

  String getCrossorigin() {
    getAttribute('crossorigin')
  }
}
