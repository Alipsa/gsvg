package se.alipsa.groovy.svg

import groovy.transform.CompileStatic

/**
 * SVG {@code <animate>} element that animates an attribute or property value over time.
 */
@CompileStatic
class Animate extends Animation<Animate> {

  static final String NAME='animate'

  /**
   * Creates a Animate.
   *
   * @param parent the parent SVG element
   */
  Animate(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   * Sets the attribute name attribute.
   *
   * @param name the name of the element
   * @return this element for chaining
   */
  Animate attributeName(String name) {
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
   * Sets the values attribute.
   *
   * @param vals the values
   * @return this element for chaining
   */
  Animate values(String vals) {
    addAttribute('values', vals)
  }

  /**
   * Returns the values value.
   *
   * @return the values value
   */
  String getValues() {
    getAttribute('values')
  }

  /**
   * Sets the dur attribute.
   *
   * @param duration the duration
   * @return this element for chaining
   */
  Animate dur(String duration) {
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
   * @param time the time value
   * @return this element for chaining
   */
  Animate begin(String time) {
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
   * @param fromNum the starting number
   * @return this element for chaining
   */
  Animate from(Number fromNum) {
    addAttribute('from', "$fromNum")
  }

  /**
   * Sets the from attribute.
   *
   * @param fromNum value
   * @return this element for chaining
   */
  Animate from(String fromNum) {
    addAttribute('from', "$fromNum")
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
   * @param to the ending value for the animation
   * @return this element for chaining
   */
  Animate to(String to) {
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
   * Sets the fill attribute.
   *
   * @param fill the fill color
   * @return this element for chaining
   */
  Animate fill(String fill) {
    addAttribute('fill', "$fill")
  }

  /**
   * Returns the fill value.
   *
   * @return the fill value
   */
  String getFill() {
    getAttribute('fill')
  }

  /**
   * Sets the calcMode attribute (animation interpolation mode).
   *
   * @param mode the calculation mode (discrete, linear, paced, spline)
   * @return this element for chaining
   */
  Animate calcMode(String mode) {
    addAttribute('calcMode', mode)
  }

  /**
   * Returns the calcMode value.
   *
   * @return the calcMode value
   */
  String getCalcMode() {
    getAttribute('calcMode')
  }

  /**
   * Sets the additive attribute.
   *
   * @param additive whether animation is additive (replace, sum)
   * @return this element for chaining
   */
  Animate additive(String additive) {
    addAttribute('additive', additive)
  }

  /**
   * Returns the additive value.
   *
   * @return the additive value
   */
  String getAdditive() {
    getAttribute('additive')
  }

  /**
   * Sets the accumulate attribute.
   *
   * @param accumulate whether animation accumulates (none, sum)
   * @return this element for chaining
   */
  Animate accumulate(String accumulate) {
    addAttribute('accumulate', accumulate)
  }

  /**
   * Returns the accumulate value.
   *
   * @return the accumulate value
   */
  String getAccumulate() {
    getAttribute('accumulate')
  }

  /**
   * Sets the end attribute.
   *
   * @param time the end time for the animation
   * @return this element for chaining
   */
  Animate end(String time) {
    addAttribute('end', time)
  }

  /**
   * Returns the end value.
   *
   * @return the end value
   */
  String getEnd() {
    getAttribute('end')
  }

  /**
   * Sets the keyTimes attribute.
   *
   * @param times semicolon-separated time values
   * @return this element for chaining
   */
  Animate keyTimes(String times) {
    addAttribute('keyTimes', times)
  }

  /**
   * Returns the keyTimes value.
   *
   * @return the keyTimes value
   */
  String getKeyTimes() {
    getAttribute('keyTimes')
  }

  /**
   * Sets the repeatDur attribute.
   *
   * @param duration total duration for repeating
   * @return this element for chaining
   */
  Animate repeatDur(String duration) {
    addAttribute('repeatDur', duration)
  }

  /**
   * Returns the repeatDur value.
   *
   * @return the repeatDur value
   */
  String getRepeatDur() {
    getAttribute('repeatDur')
  }

  /**
   * Sets the repeatCount attribute.
   *
   * @param count number of times to repeat
   * @return this element for chaining
   */
  Animate repeatCount(String count) {
    addAttribute('repeatCount', count)
  }

  /**
   * Returns the repeatCount value.
   *
   * @return the repeatCount value
   */
  String getRepeatCount() {
    getAttribute('repeatCount')
  }
}
