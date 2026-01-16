package se.alipsa.groovy.svg

import groovy.transform.CompileStatic
import groovy.transform.PackageScope
import org.dom4j.Element

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
   * Creates an Animation by adopting an existing DOM4J Element.
   * Used for cloning/copying operations.
   *
   * @param parent the parent SVG element
   * @param element the DOM4J element to adopt
   */
  @PackageScope
  Animation(SvgElement parent, Element element) {
    super(parent, element)
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
