package se.alipsa.groovy.svg

import groovy.transform.CompileStatic

/**
 * Base class for SVG animation elements that target attributes over time.
 */
@CompileStatic
abstract class Animation<T extends SvgElement<T>> extends SvgElement<T> {

  /**
   * Creates a Animation.
   *
   * @param parent the parent SVG element
   * @param name the name of the element
   */
  Animation(SvgElement<? extends SvgElement> parent, String name) {
    super(parent, name)
  }

  /**
   * Sets the repeat count attribute.
   *
   * @param repeats the number of repeats
   * @return this element for chaining
   */
  T repeatCount(String repeats) {
    addAttribute('repeatCount', repeats)
  }

  /**
   * Returns the repeat count value.
   *
   * @return the repeat count value
   */
  String getRepeatCount() {
    getAttribute('repeatCount')
  }
}
