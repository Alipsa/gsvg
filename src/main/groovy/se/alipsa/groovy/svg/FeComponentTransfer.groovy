package se.alipsa.groovy.svg

/**
 * SVG {@code <feComponentTransfer>} filter primitive that applies per-channel transfer functions.
 */
class FeComponentTransfer extends FilterElement<FeComponentTransfer> {

  static final String NAME='feComponentTransfer'

  /**
   * Creates a FeComponentTransfer.
   *
   * @param parent value
   */
  FeComponentTransfer(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
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
   * @param type value
   * @return the created element
   */
  FeFuncA addFeFuncA(String type) {
    addFeFuncA().type(type)
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
   * @param type value
   * @return the created element
   */
  FeFuncB addFeFuncB(String type) {
    addFeFuncB().type(type)
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
   * @param type value
   * @return the created element
   */
  FeFuncG addFeFuncG(String type) {
    addFeFuncG().type(type)
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
   * @param type value
   * @return the created element
   */
  FeFuncR addFeFuncR(String type) {
    addFeFuncR().type(type)
  }
}
