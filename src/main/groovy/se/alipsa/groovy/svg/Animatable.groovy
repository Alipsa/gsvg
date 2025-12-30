package se.alipsa.groovy.svg

/**
 * Trait that allows elements to host SVG animation elements.
 */
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

  List<Animation> getAnimations() {
    children.findAll { it instanceof Animation} as List<Animation>
  }

  Animation getAnimation() {
    List<Animation> animations = getAnimations()
    if (animations.size() > 0) {
      animations[0]
    } else {
      null
    }
  }

}
