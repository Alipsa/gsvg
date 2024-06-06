package se.alipsa.groovy.svg

trait Animatable<T extends SvgElement<T>> {

  private List<Animation> animations = []

  Animate addAnimate() {
    Animate animate = new Animate(this as T)
    animations << animate
    animate
  }

  AnimateMotion addAnimateMotion() {
    AnimateMotion motion = new AnimateMotion(this as T)
    animations << motion
    motion
  }

  AnimateTransform addAnimateTransform() {
    AnimateTransform transform = new AnimateTransform(this as T)
    animations << transform
    transform
  }

  Set addSet() {
    Set set = new Set(this as T)
    animations << set
    set
  }

  List<Animation> getAnimations() {
    animations
  }

  Animation getAnimation() {
    if (animations.size() > 0) {
      animations[0]
    } else {
      null
    }
  }

}