package se.alipsa.groovy.svg

/**
 * Base class for basic SVG shape elements with shared presentation attributes.
 */
class AbstractShape<T extends SvgElement<T>> extends SvgElement<T> implements Animatable<T>{

  /**
   * Creates a AbstractShape.
   *
   * @param parent the parent SVG element
   * @param name the name of the element
   */
  AbstractShape(SvgElement<? extends SvgElement> parent, String name) {
    super(parent, name)
  }

  /**
   * Sets the mask reference applied when rendering this shape (for example `url(#maskId)`).
   *
   * @param ref the reference
   * @return this element for chaining
   */
  T mask(String ref) {
    addAttribute('mask', ref)
  }

  /**
   * Returns the mask reference applied when rendering this shape.
   *
   * @return the mask value
   */
  String getMask() {
    getAttribute('mask')
  }

  /**
   * Sets the onclick event handler for this shape.
   *
   * @param value the value
   * @return this element for chaining
   */
  T onClick(String value) {
    addAttribute('onclick', value)
  }

  /**
   * Sets the fill rule used to determine the interior of the shape (for example `nonzero` or `evenodd`).
   *
   * @param value the value
   * @return this element for chaining
   */
  T fillRule(String value) {
    addAttribute('fill-rule', value)
    this as T
  }

  /**
   * Returns the fill rule used to determine the interior of the shape.
   *
   * @return the fill rule value
   */
  String getFillRule() {
    getAttribute('fill-rule')
  }

  /**
   * Sets the stroke line join style for corners (for example `miter`, `round`, or `bevel`).
   *
   * @param value the value
   * @return this element for chaining
   */
  T strokeLinejoin(String value) {
    addAttribute('stroke-linejoin', value)
    this as T
  }

  /**
   * Returns the stroke line join style for corners.
   *
   * @return the stroke linejoin value
   */
  String getStrokeLinejoin() {
    getAttribute('stroke-linejoin')
  }

  /**
   * Sets the stroke line cap style for line ends (for example `butt`, `round`, or `square`).
   *
   * @param value the value
   * @return this element for chaining
   */
  T strokeLinecap(String value) {
    addAttribute('stroke-linecap', value)
    this as T
  }

  /**
   * Returns the stroke line cap style for line ends.
   *
   * @return the stroke linecap value
   */
  String getStrokeLinecap() {
    getAttribute('stroke-linecap')
  }
}
