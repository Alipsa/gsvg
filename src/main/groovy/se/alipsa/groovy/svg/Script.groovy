package se.alipsa.groovy.svg

/**
 * SVG {@code <script>} element that holds scripting content for interactivity.
 */
class Script extends StringContentContainer<Script> {

  static final String NAME='script'

  /**
   * Creates a Script.
   *
   * @param parent the parent SVG element
   */
  Script(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   * Sets the type attribute.
   *
   * @param value the value
   * @return this element for chaining
   */
  Script type(String value) {
    addAttribute('type', value)
  }

  /**
   * Returns the type value.
   *
   * @return the type value
   */
  String getType() {
    getAttribute('type')
  }

  /**
   * Sets the href attribute.
   *
   * @param href the hyperlink URL
   * @return this element for chaining
   */
  Script href(String href) {
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
   * Sets the xlink href attribute.
   *
   * @param href the hyperlink URL
   * @return this element for chaining
   */
  Script xlinkHref(String href) {
    addAttribute(xlink('href'), href)
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
   *
   * @param value one of "anonymous", "use-credentials", ""
   * @return
   */
  Script crossorigin(String value) {
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
