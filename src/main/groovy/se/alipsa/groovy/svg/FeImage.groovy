package se.alipsa.groovy.svg

import groovy.transform.CompileStatic

/**
 * SVG {@code <feImage>} filter primitive that provides image input from a referenced resource.
 */
@CompileStatic
class FeImage extends FilterElement<FeImage> {
  static final String NAME = 'feImage'

  /**
   * Creates a FeImage.
   *
   * @param parent the parent SVG element
   */
  FeImage(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   * Sets the xlink href attribute.
   *
   * @param href the hyperlink URL
   * @return this element for chaining
   */
  FeImage xlinkHref(String href) {
    addAttribute(xlink('href'), href)
  }

  /**
   * Sets the href attribute.
   *
   * @param href the hyperlink URL
   * @return this element for chaining
   */
  FeImage href(String href) {
    addAttribute('href', href)
  }

  /**
   * Returns the href value.
   *
   * @return the href value
   */
  String getHref() {
    getAttribute('href') ?: getAttribute(xlink('href'))
  }

  /**
   * Returns the xlink href value.
   *
   * @return the xlink href value
   */
  String getXlinkHref() {
    getAttribute(xlink('href'))
  }

  /**
   * Adds an attribute to this element.
   *
   * @param name the name of the element
   * @param value the value
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

  /**
   * Returns the crossorigin value.
   *
   * @return the crossorigin value
   */
  String getCrossorigin() {
    getAttribute('crossorigin')
  }
}
