package se.alipsa.groovy.svg

class FeImage extends FilterElement<FeImage> {
  static final String NAME = 'feImage'

  FeImage(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  FeImage xlinkHref(String href) {

    addAttribute(xlink('href'), href)
  }
}
