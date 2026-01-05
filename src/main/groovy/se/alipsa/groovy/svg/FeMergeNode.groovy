package se.alipsa.groovy.svg

import groovy.transform.CompileStatic

/**
 * SVG {@code <feMergeNode>} element that references an input for feMerge.
 */
@CompileStatic
class FeMergeNode extends FilterElement<FeMergeNode> {

  static final String NAME = 'feMergeNode'

  /**
   * Creates a FeMergeNode.
   *
   * @param parent the parent SVG element
   */
  FeMergeNode(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   * Sets the in attribute.
   *
   * @param inStr the input source string identifier
   * @return this element for chaining
   */
  FeMergeNode 'in'(String inStr) {
    addAttribute('in', inStr)
  }

  /**
   * Sets the in attribute.
   *
   * @param inEnum the input source enumeration
   * @return this element for chaining
   */
  FeMergeNode 'in'(In inEnum) {
    addAttribute('in', inEnum.name())
  }

  /**
   * Returns the in value.
   *
   * @return the in value
   */
  String getIn() {
    getAttribute('in')
  }
}
