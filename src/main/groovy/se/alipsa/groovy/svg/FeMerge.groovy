package se.alipsa.groovy.svg

import groovy.transform.CompileStatic

/**
 * SVG {@code <feMerge>} filter primitive that merges multiple inputs into one.
 */
@CompileStatic
class FeMerge extends FilterElement<FeMerge> {

  static final String NAME = 'feMerge'

  /**
   * Creates a FeMerge.
   *
   * @param parent the parent SVG element
   */
  FeMerge(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   * Creates and adds a new FeMergeNode child element.
   *
   * @return the created element
   */
  FeMergeNode addFeMergeNode() {
    add(new FeMergeNode(this))
  }

  /**
   * Creates and adds a new FeMergeNode child element.
   *
   * @param id the unique identifier
   * @return the created element
   */
  FeMergeNode addFeMergeNode(String id) {
    addFeMergeNode().id(id)
  }

  FeMergeNode addFeMergeNode(Map attributes) {
    FeMergeNode elem = addFeMergeNode()
    attributes.each {
      key, value -> elem.addAttribute(String.valueOf(key), String.valueOf(value))
    }
    elem
  }
}
