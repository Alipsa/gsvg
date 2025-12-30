package se.alipsa.groovy.svg

/**
 * SVG {@code <script>} element that holds scripting content for interactivity.
 */
class Script extends StringContentContainer<Script> {

  static final String NAME='script'

  /**
   * Creates a Script.
   *
   * @param parent value
   */
  Script(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   * Sets the type attribute.
   *
   * @param value value
   * @return this element for chaining
   */
  Script type(String value) {
    addAttribute('type', value)
  }

  String getType() {
    getAttribute('type')
  }

  /**
   * Sets the href attribute.
   *
   * @param href value
   * @return this element for chaining
   */
  Script href(String href) {
    addAttribute('href', href)
  }

  String getHref() {
    getAttribute('href') ?: getAttribute(xlink('href'))
  }

  /**
   * Sets the xlink href attribute.
   *
   * @param href value
   * @return this element for chaining
   */
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
