package se.alipsa.groovy.svg

/**
 * SVG {@code <animate>} element that animates an attribute or property value over time.
 */
class Animate extends Animation<Animate> {

  static final String NAME='animate'

  /**
   * Creates a Animate.
   *
   * @param parent value
   */
  Animate(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   * Sets the attribute name attribute.
   *
   * @param name value
   * @return this element for chaining
   */
  Animate attributeName(String name) {
    addAttribute('attributeName', name)
  }

  String getAttributeName() {
    getAttribute('attributeName')
  }

  /**
   * Sets the values attribute.
   *
   * @param vals value
   * @return this element for chaining
   */
  Animate values(String vals) {
    addAttribute('values', vals)
  }

  String getValues() {
    getAttribute('values')
  }

  /**
   * Sets the dur attribute.
   *
   * @param duration value
   * @return this element for chaining
   */
  Animate dur(String duration) {
    addAttribute('dur', duration)
  }

  String getDur(){
    getAttribute('dur')
  }

  /**
   * Sets the begin attribute.
   *
   * @param time value
   * @return this element for chaining
   */
  Animate begin(String time) {
    addAttribute('begin', time)
  }

  String getBegin() {
    getAttribute('begin')
  }

  /**
   * Sets the from attribute.
   *
   * @param fromNum value
   * @return this element for chaining
   */
  Animate from(Number fromNum) {
    addAttribute('from', "$fromNum")
  }

  String getFrom() {
    getAttribute('from')
  }

  /**
   * Sets the to attribute.
   *
   * @param to value
   * @return this element for chaining
   */
  Animate to(String to) {
    addAttribute('to', to)
  }

  String getTo() {
    getAttribute('to')
  }

  /**
   * Sets the fill attribute.
   *
   * @param fill value
   * @return this element for chaining
   */
  Animate fill(String fill) {
    addAttribute('fill', "$fill")
  }

  String getFill() {
    getAttribute('fill')
  }
}
