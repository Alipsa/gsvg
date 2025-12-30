package se.alipsa.groovy.svg

/**
 * SVG {@code <mpath>} element that references a path for motion animation.
 */
class Mpath extends SvgElement<Mpath> {

  static final String NAME='mpath'

  /**
   * Creates a Mpath.
   *
   * @param parent the parent SVG element
   */
  Mpath(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   * Sets the xlink href attribute.
   *
   * @param href the hyperlink URL
   * @return this element for chaining
   */
  Mpath xlinkHref(String href) {
    addAttribute(xlink('href'), href)
  }

  /**
   * Sets the href attribute.
   *
   * @param href the hyperlink URL
   * @return this element for chaining
   */
  Mpath href(String href) {
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
