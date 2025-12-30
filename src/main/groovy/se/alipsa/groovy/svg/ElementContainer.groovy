package se.alipsa.groovy.svg

/**
 * Trait that stores child elements and provides selection helpers.
 */
trait ElementContainer {

  private List<SvgElement<? extends SvgElement>> children = []

  List<SvgElement<? extends SvgElement>> getChildren() {
    children
  }

  /**
   * Adds a child element to this container.
   *
   * @param svgElement value
   * @return the added element
   */
  <E extends SvgElement> E add(E svgElement) {
    children.add(svgElement)
    svgElement
  }

  /**
   * Returns child element(s) for the provided selector.
   *
   * @param index value
   * @return the result
   */
  SvgElement getAt(Integer index) {
    children[index]
  }

  /**
   * Returns child element(s) for the provided selector.
   *
   * @param name value
   * @return the result
   */
  List<SvgElement> getAt(String name) {
    children.stream().filter (e -> e.name == name).findAll()
  }

  /**
   * Returns child element(s) for the provided selector.
   *
   * @param clazz value
   * @return the result
   */
  List<SvgElement> getAt(Class<? extends SvgElement> clazz) {
    children.stream().filter (e -> e.class == clazz).findAll()
  }
}
