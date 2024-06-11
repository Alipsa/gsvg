package se.alipsa.groovy.svg

class FeMerge extends FilterElement<FeMerge> {

  static final String NAME = 'feMerge'

  FeMerge(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  FeMergeNode addFeMergeNode() {
    add(new FeMergeNode(this))
  }

  FeMergeNode addFeMergeNode(String id) {
    addFeMergeNode().id(id)
  }
}
