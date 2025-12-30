package se.alipsa.groovy.svg

/**
 * SVG {@code <animateMotion>} element that animates motion along a path.
 */
class AnimateMotion extends Animation<AnimateMotion> {

  static final String NAME='animateMotion'

  /**
   * Creates a AnimateMotion.
   *
   * @param parent value
   */
  AnimateMotion(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  List<Mpath> children = []

  /**
   * Sets the path attribute.
   *
   * @param path value
   * @return this element for chaining
   */
  AnimateMotion path(String path) {
    addAttribute('path', path)
  }

  String getPath() {
    getAttribute('path')
  }

  /**
   * Sets the dur attribute.
   *
   * @param duration value
   * @return this element for chaining
   */
  AnimateMotion dur(String duration) {
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
  AnimateMotion begin(String time) {
    addAttribute('begin', time)
  }

  String getBegin() {
    getAttribute('begin')
  }

  /**
   * Sets the rotate attribute.
   *
   * @param value value
   * @return this element for chaining
   */
  AnimateMotion rotate(String value) {
    addAttribute('rotate', value)
  }

  String getRotate() {
    getAttribute('rotate')
  }

  /**
   * Creates and adds a new Mpath child element.
   *
   * @return the created element
   */
  Mpath addMpath() {
    Mpath mp = new Mpath(this)
    children << mp
    mp
  }
}
