package se.alipsa.groovy.svg

interface LinearGradientContainer {

  default LinearGradient addLinearGradient() {
    LinearGradient lg = new LinearGradient(this)
    // we rely on the fact that all elements that can have a LinearGradient is also an AbstractElementContainer
    // (which has the children list)
    children.add(lg)
    lg
  }
}