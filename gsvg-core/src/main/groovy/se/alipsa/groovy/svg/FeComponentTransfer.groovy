package se.alipsa.groovy.svg



import groovy.transform.PackageScope
import org.dom4j.Element
import groovy.transform.CompileStatic

/**
 * SVG {@code <feComponentTransfer>} filter primitive that applies per-channel transfer functions.
 */
@CompileStatic
class FeComponentTransfer extends FilterElement<FeComponentTransfer> {

  static final String NAME='feComponentTransfer'

  /**
   * Creates a FeComponentTransfer.
   *
   * @param parent the parent SVG element
   */
  FeComponentTransfer(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  @PackageScope
  FeComponentTransfer(SvgElement parent, Element element) {
    super(parent, element)
  }


  /**
   * Creates and adds a new FeFuncA child element.
   *
   * @return the created element
   */
  FeFuncA addFeFuncA() {
    add(new FeFuncA(this))
  }

  /**
   * Creates and adds a new FeFuncA child element.
   *
   * @param type the type
   * @return the created element
   */
  FeFuncA addFeFuncA(String type) {
    addFeFuncA().type(type)
  }

  FeFuncA addFeFuncA(Map attributes) {
    FeFuncA elem = addFeFuncA()
    attributes.each {
      key, value -> elem.addAttribute(String.valueOf(key), value)
    }
    elem
  }

  /**
   * Creates and adds a new FeFuncB child element.
   *
   * @return the created element
   */
  FeFuncB addFeFuncB() {
    add(new FeFuncB(this))
  }

  /**
   * Creates and adds a new FeFuncB child element.
   *
   * @param type the type
   * @return the created element
   */
  FeFuncB addFeFuncB(String type) {
    addFeFuncB().type(type)
  }

  FeFuncB addFeFuncB(Map attributes) {
    FeFuncB elem = addFeFuncB()
    attributes.each {
      key, value -> elem.addAttribute(String.valueOf(key), value)
    }
    elem
  }

  /**
   * Creates and adds a new FeFuncG child element.
   *
   * @return the created element
   */
  FeFuncG addFeFuncG() {
    add(new FeFuncG(this))
  }

  /**
   * Creates and adds a new FeFuncG child element.
   *
   * @param type the type
   * @return the created element
   */
  FeFuncG addFeFuncG(String type) {
    addFeFuncG().type(type)
  }

  FeFuncG addFeFuncG(Map attributes) {
    FeFuncG elem = addFeFuncG()
    attributes.each {
      key, value -> elem.addAttribute(String.valueOf(key), value)
    }
    elem
  }

  /**
   * Creates and adds a new FeFuncR child element.
   *
   * @return the created element
   */
  FeFuncR addFeFuncR() {
    add(new FeFuncR(this))
  }

  /**
   * Creates and adds a new FeFuncR child element.
   *
   * @param type the type
   * @return the created element
   */
  FeFuncR addFeFuncR(String type) {
    addFeFuncR().type(type)
  }

  FeFuncR addFeFuncR(Map attributes) {
    FeFuncR elem = addFeFuncR()
    attributes.each {
      key, value -> elem.addAttribute(String.valueOf(key), value)
    }
    elem
  }
}