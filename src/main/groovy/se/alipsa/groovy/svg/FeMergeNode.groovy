package se.alipsa.groovy.svg

/**
 * SVG {@code <feMergeNode>} element that references an input for feMerge.
 */
class FeMergeNode extends FilterElement<FeMergeNode> {

  static final String NAME = 'feMergeNode'

  /**
   * Creates a FeMergeNode.
   *
   * @param parent value
   */
  FeMergeNode(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   * Sets the in attribute.
   *
   * @param inStr value
   * @return this element for chaining
   */
  FeMergeNode in(String inStr) {
    addAttribute('in', inStr)
  }

  /**
   * Sets the in attribute.
   *
   * @param inEnum value
   * @return this element for chaining
   */
  FeMergeNode in(In inEnum) {
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
