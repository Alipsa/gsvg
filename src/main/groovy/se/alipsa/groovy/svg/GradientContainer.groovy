package se.alipsa.groovy.svg

import groovy.transform.CompileStatic

/**
 * Trait for elements that can contain gradient definitions.
 */
@CompileStatic
trait GradientContainer extends ElementContainer {

  /**
   * Creates and adds a new LinearGradient child element.
   *
   * @return the created element
   */
  LinearGradient addLinearGradient() {
    add(new LinearGradient(this as SvgElement<? extends SvgElement>))
  }

  /**
   * Creates and adds a new RadialGradient child element.
   *
   * @return the created element
   */
  RadialGradient addRadialGradient() {
    add(new RadialGradient(this as SvgElement<? extends SvgElement>))
  }


}
