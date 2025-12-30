package se.alipsa.groovy.svg

/**
 * SVG {@code <feImage>} filter primitive that provides image input from a referenced resource.
 */
class FeImage extends FilterElement<FeImage> {
  static final String NAME = 'feImage'

  /**
   * Creates a FeImage.
   *
   * @param parent value
   */
  FeImage(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   * Sets the xlink href attribute.
   *
   * @param href value
   * @return this element for chaining
   */
  FeImage xlinkHref(String href) {
    addAttribute(xlink('href'), href)
  }

  /**
   * Sets the href attribute.
   *
   * @param href value
   * @return this element for chaining
   */
  FeImage href(String href) {
    addAttribute('href', href)
  }

  String getHref() {
    getAttribute('href') ?: getAttribute(xlink('href'))
  }

  String getXlinkHref() {
    getAttribute(xlink('href'))
  }

  /**
   * Adds an attribute to this element.
   *
   * @param name value
   * @param value value
   * @return the created element
   */
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
