package se.alipsa.groovy.svg

import groovy.transform.CompileStatic

/**
 * Trait that stores child elements and provides selection helpers.
 */
@CompileStatic
trait ElementContainer {

  private List<SvgElement<? extends SvgElement>> children = []

  /**
   * Returns the children value.
   *
   * @return the children value
   */
  List<SvgElement<? extends SvgElement>> getChildren() {
    children
  }

  /**
   * Adds a child element to this container.
   *
   * @param svgElement the SVG element
   * @return the added element
   */
  <E extends SvgElement> E add(E svgElement) {
    children.add(svgElement)
    svgElement
  }

  /**
   * Returns child element(s) for the provided selector.
   *
   * @param index the index
   * @return the result
   */
  SvgElement getAt(Integer index) {
    children[index]
  }

  /**
   * Returns child element(s) for the provided selector.
   *
   * @param name the name of the element
   * @return the result
   */
  List<SvgElement> getAt(String name) {
    children.stream().filter (e -> e.name == name).findAll()
  }

  /**
   * Returns child element(s) for the provided selector.
   *
   * @param clazz the CSS class
   * @return the result
   */
  List<SvgElement> getAt(Class<? extends SvgElement> clazz) {
    children.stream().filter (e -> e.class == clazz).findAll()
  }
}
