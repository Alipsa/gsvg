package se.alipsa.groovy.svg

trait Animatable<T extends SvgElement<T>> implements ElementContainer {

  Animate addAnimate() {
    add(new Animate(this as T))
  }

  AnimateMotion addAnimateMotion() {
    add(new AnimateMotion(this as T))
  }

  AnimateTransform addAnimateTransform() {
    add(new AnimateTransform(this as T))
  }

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