package se.alipsa.groovy.svg

import groovy.transform.CompileStatic
import org.dom4j.Element

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
   * Creates a Defs by adopting an existing DOM4J Element.
   * Used for cloning/copying operations.
   *
   * @param parent the parent SVG element
   * @param element the DOM4J element to adopt
   */
  Defs(SvgElement parent, Element element) {
    super(parent, element)
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
   * Creates and adds a new Marker child element with attributes.
   *
   * @param attributes map of attributes to apply
   * @return the created element
   */
  Marker addMarker(Map attributes) {
    Marker marker = addMarker()
    attributes.each {
      key, value -> marker.addAttribute(String.valueOf(key), value)
    }
    marker
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
   * Creates and adds a new Pattern child element with attributes.
   *
   * @param attributes map of attributes to apply
   * @return the created element
   */
  Pattern addPattern(Map attributes) {
    Pattern pattern = addPattern()
    attributes.each {
      key, value -> pattern.addAttribute(String.valueOf(key), value)
    }
    pattern
  }

  /**
   * Creates and adds a new Symbol child element.
   *
   * @return the created element
   */
  Symbol addSymbol() {
    add(new Symbol(this))
  }

  /**
   * Creates and adds a new Symbol child element with attributes.
   *
   * @param attributes map of attributes to apply
   * @return the created element
   */
  Symbol addSymbol(Map attributes) {
    Symbol symbol = addSymbol()
    attributes.each {
      key, value -> symbol.addAttribute(String.valueOf(key), value)
    }
    symbol
  }
}
