package se.alipsa.groovy.svg

abstract class LightSourceElement<T extends FilterElement<T>> extends FilterElement<T> {

  LightSourceElement(SvgElement<? extends SvgElement> parent, String name) {
    super(parent, name)
  }
}
