package se.alipsa.groovy.svg

/**
 * SVG {@code <animateTransform>} element that animates a transform over time.
 */
class AnimateTransform extends Animation<AnimateTransform> {

  static final String NAME='animateTransform'

  /**
   * Creates a AnimateTransform.
   *
   * @param parent value
   */
  AnimateTransform(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   * Sets the attribute name attribute.
   *
   * @param name value
   * @return this element for chaining
   */
  AnimateTransform attributeName(String name) {
    addAttribute('attributeName', name)
  }

  /**
   * Returns the attribute name value.
   *
   * @return the attribute name value
   */
  String getAttributeName() {
    getAttribute('attributeName')
  }

  /**
   * Sets the dur attribute.
   *
   * @param duration value
   * @return this element for chaining
   */
  AnimateTransform dur(String duration) {
    addAttribute('dur', duration)
  }

  /**
   * Returns the dur value.
   *
   * @return the dur value
   */
  String getDur(){
    getAttribute('dur')
  }

  /**
   * Sets the begin attribute.
   *
   * @param time value
   * @return this element for chaining
   */
  AnimateTransform begin(String time) {
    addAttribute('begin', time)
  }

  /**
   * Returns the begin value.
   *
   * @return the begin value
   */
  String getBegin() {
    getAttribute('begin')
  }

  /**
   * Sets the from attribute.
   *
   * @param fromNum value
   * @return this element for chaining
   */
  AnimateTransform from(Number fromNum) {
    addAttribute('from', "$fromNum")
  }

  /**
   * Sets the from attribute.
   *
   * @param from value
   * @return this element for chaining
   */
  AnimateTransform from(String from) {
    addAttribute('from', from)
  }

  /**
   * Returns the from value.
   *
   * @return the from value
   */
  String getFrom() {
    getAttribute('from')
  }

  /**
   * Sets the to attribute.
   *
   * @param to value
   * @return this element for chaining
   */
  AnimateTransform to(String to) {
    addAttribute('to', to)
  }

  /**
   * Returns the to value.
   *
   * @return the to value
   */
  String getTo() {
    getAttribute('to')
  }

  /**
   * Sets the type attribute.
   *
   * @param type value
   * @return this element for chaining
   */
  AnimateTransform type(String type) {
    addAttribute('type', type)
  }

  /**
   * Returns the type value.
   *
   * @return the type value
   */
  String getType() {
    getAttribute('type')
  }
}
