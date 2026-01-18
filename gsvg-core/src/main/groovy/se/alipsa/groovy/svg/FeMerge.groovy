package se.alipsa.groovy.svg



import groovy.transform.PackageScope
import org.dom4j.Element
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

  @PackageScope
  FeMerge(SvgElement parent, Element element) {
    super(parent, element)
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

  /**
   * Creates and adds a new FeMergeNode child element with attributes.
   *
   * @param attributes map of attributes to apply
   * @return the created element
   */
  FeMergeNode addFeMergeNode(Map attributes) {
    FeMergeNode elem = addFeMergeNode()
    attributes.each {
      key, value -> elem.addAttribute(String.valueOf(key), value)
    }
    elem
  }
}
