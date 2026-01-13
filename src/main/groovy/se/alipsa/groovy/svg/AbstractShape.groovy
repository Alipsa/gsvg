package se.alipsa.groovy.svg

import groovy.transform.CompileStatic
import org.dom4j.Element

/**
 * Base class for basic SVG shape elements with shared presentation attributes.
 */
@CompileStatic
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

  /**
   * Sets the stroke dash pattern used to draw the outline (for example `"4 2"`).
   *
   * @param dashArray value
   * @return this element for chaining
   */
  T strokeDasharray(String dashArray) {
    addAttribute('stroke-dasharray', dashArray)
    this as T
  }

  /**
   * Returns the stroke dash pattern used to draw the outline.
   *
   * @return the stroke dasharray value
   */
  String getStrokeDasharray() {
    getAttribute('stroke-dasharray')
  }

  /**
   * Sets the stroke opacity (0 to 1) used when painting the outline.
   *
   * @param alpha value
   * @return this element for chaining
   */
  T strokeOpacity(Number alpha) {
    addAttribute('stroke-opacity', alpha)
    this as T
  }

  /**
   * Sets the stroke opacity using a string value (for example `"0.5"` or `"50%"`).
   *
   * @param alpha value
   * @return this element for chaining
   */
  T strokeOpacity(String alpha) {
    addAttribute('stroke-opacity', alpha)
    this as T
  }

  /**
   * Returns the stroke opacity used when painting the outline.
   *
   * @return the stroke opacity value
   */
  String getStrokeOpacity() {
    getAttribute('stroke-opacity')
  }

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

  /**
   * Sets the transform attribute directly as a string.
   *
   * @param value the transform value
   * @return this element for chaining
   */
  T transform(String value) {
    addAttribute('transform', value)
    this as T
  }

  /**
   * Returns the transform attribute value.
   *
   * @return the transform value
   */
  String getTransform() {
    getAttribute('transform')
  }

  /**
   * Appends a rotate transform to the existing transform attribute.
   * If no transform exists, creates a new one.
   *
   * @param angle the rotation angle in degrees
   * @return this element for chaining
   */
  T rotate(Number angle) {
    appendTransform("rotate($angle)")
  }

  /**
   * Appends a rotate transform around a specific center point.
   *
   * @param angle the rotation angle in degrees
   * @param cx the x-coordinate of the rotation center
   * @param cy the y-coordinate of the rotation center
   * @return this element for chaining
   */
  T rotate(Number angle, Number cx, Number cy) {
    appendTransform("rotate($angle $cx $cy)")
  }

  /**
   * Appends a translate transform to the existing transform attribute.
   *
   * @param tx the x-axis translation
   * @param ty the y-axis translation
   * @return this element for chaining
   */
  T translate(Number tx, Number ty) {
    appendTransform("translate($tx $ty)")
  }

  /**
   * Appends a translate transform along the x-axis only.
   *
   * @param tx the x-axis translation
   * @return this element for chaining
   */
  T translate(Number tx) {
    appendTransform("translate($tx)")
  }

  /**
   * Appends a scale transform to the existing transform attribute.
   *
   * @param sx the x-axis scale factor
   * @param sy the y-axis scale factor
   * @return this element for chaining
   */
  T scale(Number sx, Number sy) {
    appendTransform("scale($sx $sy)")
  }

  /**
   * Appends a uniform scale transform (same factor for both axes).
   *
   * @param s the scale factor
   * @return this element for chaining
   */
  T scale(Number s) {
    appendTransform("scale($s)")
  }

  /**
   * Appends a skewX transform (skew along the x-axis).
   *
   * @param angle the skew angle in degrees
   * @return this element for chaining
   */
  T skewX(Number angle) {
    appendTransform("skewX($angle)")
  }

  /**
   * Appends a skewY transform (skew along the y-axis).
   *
   * @param angle the skew angle in degrees
   * @return this element for chaining
   */
  T skewY(Number angle) {
    appendTransform("skewY($angle)")
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
   * Helper method to append a transform to the existing transform attribute.
   * If the transform attribute doesn't exist, it creates it.
   * If it exists, it appends the new transform with a space separator.
   *
   * @param transformValue the transform to append
   * @return this element for chaining
   */
  private T appendTransform(String transformValue) {
    String existing = getAttribute('transform')
    if (existing == null || existing.isEmpty()) {
      addAttribute('transform', transformValue)
    } else {
      addAttribute('transform', "$existing $transformValue")
    }
    this as T
  }
}
