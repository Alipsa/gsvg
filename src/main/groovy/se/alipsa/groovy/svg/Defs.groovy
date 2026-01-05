package se.alipsa.groovy.svg

import groovy.transform.CompileStatic

/**
 * SVG {@code <defs>} element that holds reusable definitions not rendered directly.
 */
@CompileStatic
class Defs extends AbstractElementContainer<Defs> implements GradientContainer, Animatable<Defs> {

  static final String NAME = 'defs'

  /**
   * Creates a Defs.
   *
   * @param parent the parent SVG element
   */
  Defs(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   * Creates and adds a new Marker child element.
   *
   * @return the created element
   */
  Marker addMarker() {
    add(new Marker(this))
  }

  /**
   * Creates and adds a new Marker child element.
   *
   * @param id the unique identifier
   * @return the created element
   */
  Marker addMarker(String id) {
    addMarker().id(id)
  }

  /**
   * Creates and adds a new Pattern child element.
   *
   * @return the created element
   */
  Pattern addPattern() {
    add(new Pattern(this))
  }

  /**
   * Creates and adds a new Pattern child element.
   *
   * @param id the unique identifier
   * @return the created element
   */
  Pattern addPattern(String id) {
    add(new Pattern(this).id(id))
  }

  /**
   * Creates and adds a new Symbol child element.
   *
   * @return the created element
   */
  Symbol addSymbol() {
    add(new Symbol(this))
  }
}
