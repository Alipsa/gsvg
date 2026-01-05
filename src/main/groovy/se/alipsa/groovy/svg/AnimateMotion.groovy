package se.alipsa.groovy.svg

import groovy.transform.CompileStatic

/**
 * SVG {@code <animateMotion>} element that animates motion along a path.
 */
@CompileStatic
class AnimateMotion extends Animation<AnimateMotion> {

  static final String NAME='animateMotion'

  /**
   * Creates a AnimateMotion.
   *
   * @param parent the parent SVG element
   */
  AnimateMotion(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  List<Mpath> children = []

  /**
   * Sets the path attribute.
   *
   * @param path the path
   * @return this element for chaining
   */
  AnimateMotion path(String path) {
    addAttribute('path', path)
  }

  /**
   * Returns the path value.
   *
   * @return the path value
   */
  String getPath() {
    getAttribute('path')
  }

  /**
   * Sets the dur attribute.
   *
   * @param duration the duration
   * @return this element for chaining
   */
  AnimateMotion dur(String duration) {
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
  AnimateMotion begin(String time) {
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
   * Sets the rotate attribute.
   *
   * @param value the value
   * @return this element for chaining
   */
  AnimateMotion rotate(String value) {
    addAttribute('rotate', value)
  }

  /**
   * Returns the rotate value.
   *
   * @return the rotate value
   */
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
