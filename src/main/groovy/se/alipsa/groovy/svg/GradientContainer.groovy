package se.alipsa.groovy.svg

/**
 * This trait should be implemented by the svg elements that can have a Gradient (Linear or Radial)
 */
trait GradientContainer extends ElementContainer {

  LinearGradient addLinearGradient() {
    add(new LinearGradient(this as SvgElement<? extends SvgElement>))
  }

  RadialGradient addRadialGradient() {
    add(new RadialGradient(this as SvgElement<? extends SvgElement>))
  }


}