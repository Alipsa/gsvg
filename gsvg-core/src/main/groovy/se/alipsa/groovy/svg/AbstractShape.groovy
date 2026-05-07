package se.alipsa.groovy.svg

import groovy.transform.CompileStatic
import org.dom4j.Element

/**
 * Base class for basic SVG shape elements with shared presentation attributes.
 */
@CompileStatic
class AbstractShape<T extends SvgElement<T>> extends SvgElement<T> implements Animatable<T>, PresentationAttributes<T> {

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
   * Creates an AbstractShape by adopting an existing DOM4J Element.
   * Used for cloning/copying operations.
   *
   * @param parent the parent SVG element
   * @param element the DOM4J element to adopt
   */
  AbstractShape(SvgElement<? extends SvgElement> parent, Element element) {
    super(parent, element)
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

  // stroke, strokeWidth, strokeOpacity, strokeDasharray, strokeLinecap, strokeLinejoin
  // are provided by PresentationAttributes trait

  /**
   * Sets the stroke miter limit used when {@code stroke-linejoin} is {@code miter}.
   *
   * @param limit value
   * @return this element for chaining
   */
  T strokeMiterlimit(Number limit) {
    addAttribute('stroke-miterlimit', limit)
    this as T
  }

  /**
   * Sets the stroke miter limit using a string value.
   *
   * @param limit value
   * @return this element for chaining
   */
  T strokeMiterlimit(String limit) {
    addAttribute('stroke-miterlimit', limit)
    this as T
  }

  /**
   * Returns the stroke miter limit used when {@code stroke-linejoin} is {@code miter}.
   *
   * @return the stroke miterlimit value
   */
  String getStrokeMiterlimit() {
    getAttribute('stroke-miterlimit')
  }

  /**
   * Sets the stroke dash offset, which shifts where the dash pattern starts.
   *
   * @param offset value
   * @return this element for chaining
   */
  T strokeDashoffset(Number offset) {
    addAttribute('stroke-dashoffset', offset)
    this as T
  }

  /**
   * Sets the stroke dash offset using a string value (for example `"2"` or `"5px"`).
   *
   * @param offset value
   * @return this element for chaining
   */
  T strokeDashoffset(String offset) {
    addAttribute('stroke-dashoffset', offset)
    this as T
  }

  /**
   * Returns the stroke dash offset, which shifts where the dash pattern starts.
   *
   * @return the stroke dashoffset value
   */
  String getStrokeDashoffset() {
    getAttribute('stroke-dashoffset')
  }

  // fill, getFill are provided by PresentationAttributes trait

  /**
   * Sets the fill opacity (0 to 1) used when painting the interior.
   *
   * @param alpha value
   * @return this element for chaining
   */
  T fillOpacity(Number alpha) {
    addAttribute('fill-opacity', alpha)
    this as T
  }

  /**
   * Sets the fill opacity using a string value (for example `"0.5"` or `"50%"`).
   *
   * @param alpha value
   * @return this element for chaining
   */
  T fillOpacity(String alpha) {
    addAttribute('fill-opacity', alpha)
    this as T
  }

  /**
   * Returns the fill opacity used when painting the interior.
   *
   * @return the fill opacity value
   */
  String getFillOpacity() {
    getAttribute('fill-opacity')
  }

  // opacity, getOpacity are provided by PresentationAttributes trait

  // transform, getTransform, rotate(angle,cx,cy), translate, scale, skewX, skewY
  // are provided by PresentationAttributes trait

  /**
   * Appends a rotate transform to the existing transform attribute.
   * If no transform exists, creates a new one.
   *
   * <p>Note: Text elements cannot use this single-arg overload because it
   * conflicts with the per-character {@code rotate} SVG attribute.
   * Use {@code rotate(angle, cx, cy)} or {@code transform("rotate(...)")} instead.</p>
   *
   * @param angle the rotation angle in degrees
   * @return this element for chaining
   */
  T rotate(Number angle) {
    appendTransform("rotate($angle)")
  }

  /**
   * Appends a matrix transform.
   *
   * @param a the a component of the matrix
   * @param b the b component of the matrix
   * @param c the c component of the matrix
   * @param d the d component of the matrix
   * @param e the e component of the matrix
   * @param f the f component of the matrix
   * @return this element for chaining
   */
  T matrix(Number a, Number b, Number c, Number d, Number e, Number f) {
    appendTransform("matrix($a $b $c $d $e $f)")
  }

  /**
   * Sets the marker reference to draw at the start of the shape (for example {@code url(#markerId)}).
   *
   * @param start the start marker reference
   * @return this element for chaining
   */
  T markerStart(String start) {
    addAttribute('marker-start', start)
  }

  /**
   * Returns the marker reference drawn at the start of the shape.
   *
   * @return the marker-start value
   */
  String getMarkerStart() {
    getAttribute('marker-start')
  }

  /**
   * Sets the marker reference to draw at the midpoints of the shape (for example {@code url(#markerId)}).
   *
   * @param mid the mid marker reference
   * @return this element for chaining
   */
  T markerMid(String mid) {
    addAttribute('marker-mid', mid)
  }

  /**
   * Returns the marker reference drawn at the midpoints of the shape.
   *
   * @return the marker-mid value
   */
  String getMarkerMid() {
    getAttribute('marker-mid')
  }

  /**
   * Sets the marker reference to draw at the end of the shape (for example {@code url(#markerId)}).
   *
   * @param end the end marker reference
   * @return this element for chaining
   */
  T markerEnd(String end) {
    addAttribute('marker-end', end)
  }

  /**
   * Returns the marker reference drawn at the end of the shape.
   *
   * @return the marker-end value
   */
  String getMarkerEnd() {
    getAttribute('marker-end')
  }
}
