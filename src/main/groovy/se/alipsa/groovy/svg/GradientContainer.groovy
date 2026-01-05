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

  LinearGradient addLinearGradient(Map attributes) {
    LinearGradient lg = addLinearGradient()
    attributes.each {
      key, value -> lg.addAttribute(String.valueOf(key), String.valueOf(value))
    }
    lg
  }

  /**
   * Creates and adds a new RadialGradient child element.
   *
   * @return the created element
   */
  RadialGradient addRadialGradient() {
    add(new RadialGradient(this as SvgElement<? extends SvgElement>))
  }

  RadialGradient addRadialGradient(Map attributes) {
    RadialGradient rg = addRadialGradient()
    attributes.each {
      key, value -> rg.addAttribute(String.valueOf(key), String.valueOf(value))
    }
    rg
  }


}
