package se.alipsa.groovy.svg

import groovy.transform.CompileStatic

/**
 * Base class for light source elements used by lighting filter primitives.
 */
@CompileStatic
abstract class LightSourceElement<T extends FilterElement<T>> extends FilterElement<T> {

  /**
   * Creates a LightSourceElement.
   *
   * @param parent the parent SVG element
   * @param name the name of the element
   */
  LightSourceElement(SvgElement<? extends SvgElement> parent, String name) {
    super(parent, name)
  }
}
