package se.alipsa.groovy.svg

/**
 * SVG {@code <mpath>} element that references a path for motion animation.
 */
class Mpath extends SvgElement<Mpath> {

  static final String NAME='mpath'

  /**
   * Creates a Mpath.
   *
   * @param parent value
   */
  Mpath(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   * Sets the xlink href attribute.
   *
   * @param href value
   * @return this element for chaining
   */
  Mpath xlinkHref(String href) {
    addAttribute(xlink('href'), href)
  }

  /**
   * Sets the href attribute.
   *
   * @param href value
   * @return this element for chaining
   */
  Mpath href(String href) {
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
