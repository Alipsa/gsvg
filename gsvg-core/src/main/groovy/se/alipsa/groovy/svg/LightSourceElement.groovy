package se.alipsa.groovy.svg

import groovy.transform.CompileStatic
import groovy.transform.PackageScope
import org.dom4j.Element

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

  /**
   * Creates a LightSourceElement by adopting an existing DOM4J Element.
   * Used for cloning/copying operations.
   *
   * @param parent the parent SVG element
   * @param element the DOM4J element to adopt
   */
  @PackageScope
  LightSourceElement(SvgElement parent, Element element) {
    super(parent, element)
  }
}
