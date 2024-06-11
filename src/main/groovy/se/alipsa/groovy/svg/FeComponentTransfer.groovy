package se.alipsa.groovy.svg

class FeComponentTransfer extends FilterElement<FeComponentTransfer> {

  static final String NAME='feComponentTransfer'

  FeComponentTransfer(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  FeFuncA addFeFuncA() {
    add(new FeFuncA(this))
  }

  FeFuncA addFeFuncA(String type) {
    addFeFuncA().type(type)
  }

  FeFuncB addFeFuncB() {
    add(new FeFuncB(this))
  }

  FeFuncB addFeFuncB(String type) {
    addFeFuncB().type(type)
  }

  FeFuncG addFeFuncG() {
    add(new FeFuncG(this))
  }

  FeFuncG addFeFuncG(String type) {
    addFeFuncG().type(type)
  }

  FeFuncR addFeFuncR() {
    add(new FeFuncR(this))
  }

  FeFuncR addFeFuncR(String type) {
    addFeFuncR().type(type)
  }
}
