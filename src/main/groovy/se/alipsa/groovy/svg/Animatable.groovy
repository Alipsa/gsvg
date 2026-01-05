package se.alipsa.groovy.svg

import groovy.transform.CompileStatic

/**
 * Trait that allows elements to host SVG animation elements.
 */
@CompileStatic
trait Animatable<T extends SvgElement<T>> implements ElementContainer {

  /**
   * Creates and adds a new Animate child element.
   *
   * @return the created element
   */
  Animate addAnimate() {
    add(new Animate(this as T))
  }

  /**
   * Creates and adds a new AnimateMotion child element.
   *
   * @return the created element
   */
  AnimateMotion addAnimateMotion() {
    add(new AnimateMotion(this as T))
  }

  /**
   * Creates and adds a new AnimateTransform child element.
   *
   * @return the created element
   */
  AnimateTransform addAnimateTransform() {
    add(new AnimateTransform(this as T))
  }

  /**
   * Creates and adds a new Set child element.
   *
   * @return the created element
   */
  Set addSet() {
    add(new Set(this as T))
  }

  /**
   * Returns the animations value.
   *
   * @return the animations value
   */
  List<Animation> getAnimations() {
    children.findAll { it instanceof Animation} as List<Animation>
  }

  /**
   * Returns the animation value.
   *
   * @return the animation value
   */
  Animation getAnimation() {
    List<Animation> animations = getAnimations()
    if (animations.size() > 0) {
      animations[0]
    } else {
      null
    }
  }

}
