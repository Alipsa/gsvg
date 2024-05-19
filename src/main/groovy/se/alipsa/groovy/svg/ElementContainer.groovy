package se.alipsa.groovy.svg

trait ElementContainer {

  private List<SvgElement<? extends SvgElement>> children = []

  List<SvgElement<? extends SvgElement>> getChildren() {
    children
  }

  <E extends SvgElement> E add(E svgElement) {
    children.add(svgElement)
    svgElement
  }

  SvgElement getAt(Integer index) {
    children[index]
  }

  List<SvgElement> getAt(String name) {
    children.stream().filter (e -> e.name == name).findAll()
  }

  List<SvgElement> getAt(Class<? extends SvgElement> clazz) {
    children.stream().filter (e -> e.class == clazz).findAll()
  }
}