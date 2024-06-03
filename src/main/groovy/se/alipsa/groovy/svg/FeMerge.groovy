package se.alipsa.groovy.svg

class FeMerge extends FilterElement<FeMerge> {

  static final String NAME = 'feMerge'

  List<FeMergeNode> mergeNodes = []

  FeMerge(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  FeMergeNode addFeMergeNode() {
    FeMergeNode node = new FeMergeNode(this)
    mergeNodes << node
    node
  }

  FeMergeNode addFeMergeNode(String id) {
    addFeMergeNode().id(id)
  }
}
