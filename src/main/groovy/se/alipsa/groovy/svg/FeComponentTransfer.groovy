package se.alipsa.groovy.svg

class FeComponentTransfer extends FilterElement<FeComponentTransfer> {

  static final String NAME='feComponentTransfer'

  List<FilterFunction> functions = []

  FeComponentTransfer(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  FeFuncA addFeFuncA() {
    def feFunc = new FeFuncA(this)
    functions << feFunc
    feFunc
  }

  FeFuncA addFeFuncA(String type) {
    addFeFuncA().type(type)
  }

  FeFuncB addFeFuncB() {
    def feFunc = new FeFuncB(this)
    functions << feFunc
    feFunc
  }

  FeFuncB addFeFuncB(String type) {
    addFeFuncB().type(type)
  }

  FeFuncG addFeFuncG() {
    def feFunc = new FeFuncG(this)
    functions << feFunc
    feFunc
  }

  FeFuncG addFeFuncG(String type) {
    addFeFuncG().type(type)
  }

  FeFuncR addFeFuncR() {
    def feFunc = new FeFuncR(this)
    functions << feFunc
    feFunc
  }

  FeFuncR addFeFuncR(String type) {
    addFeFuncR().type(type)
  }
}
