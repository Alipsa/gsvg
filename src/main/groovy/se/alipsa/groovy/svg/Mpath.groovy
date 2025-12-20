package se.alipsa.groovy.svg

class Mpath extends SvgElement<Mpath> {

  static final String NAME='mpath'

  Mpath(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  Mpath xlinkHref(String href) {
    addAttribute(xlink('href'), href)
  }

  Mpath href(String href) {
    addAttribute('href', href)
  }

  String getHref() {
    getAttribute('href') ?: getAttribute(xlink('href'))
  }

  String getXlinkHref() {
    getAttribute(xlink('href'))
  }

  @Override
  Mpath addAttribute(String name, Object value) {
    if ('href' == name) {
      element.addAttribute('href', "$value")
      return this
    }
    if ('xlink:href' == name) {
      element.addAttribute(xlink('href'), "$value")
      return this
    }
    super.addAttribute(name, value) as Mpath
  }
}
