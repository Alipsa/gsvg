package se.alipsa.groovy.svg

class Mpath extends SvgElement<Mpath> {

  static final String NAME='mpath'

  Mpath(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  Mpath xlinkHref(String href) {
    addAttribute(xlink('href'), href)
  }

  String getXlinkHref() {
    getAttribute(xlink('href'))
  }
}
