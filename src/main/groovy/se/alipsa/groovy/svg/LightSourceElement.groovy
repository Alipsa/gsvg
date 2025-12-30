package se.alipsa.groovy.svg

/**
 * Base class for light source elements used by lighting filter primitives.
 */
abstract class LightSourceElement<T extends FilterElement<T>> extends FilterElement<T> {

  /**
   * Creates a LightSourceElement.
   *
   * @param parent value
   * @param name value
   */
  LightSourceElement(SvgElement<? extends SvgElement> parent, String name) {
    super(parent, name)
  }
}
