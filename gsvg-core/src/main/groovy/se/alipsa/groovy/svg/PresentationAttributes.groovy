package se.alipsa.groovy.svg

import groovy.transform.CompileStatic
import groovy.transform.SelfType
import se.alipsa.groovy.svg.utils.Color

/**
 * Trait providing SVG presentation attributes shared by shapes and text elements.
 *
 * <p>Covers fill, stroke, opacity, and transform convenience methods per the SVG spec.
 * Implementing classes get fluent setters that return {@code this} for chaining.</p>
 */
@CompileStatic
@SelfType(SvgElement)
trait PresentationAttributes<T extends SvgElement<T>> {

  /**
   * Sets the fill paint.
   *
   * @param fill the fill color
   * @return this element for chaining
   */
  T fill(String fill) {
    addAttribute('fill', fill)
  }

  /**
   * Sets the fill paint using a Color object.
   *
   * @param color the fill color
   * @return this element for chaining
   */
  T fill(Color color) {
    addAttribute('fill', color.toString())
  }

  /**
   * Sets the fill paint using a {@code java.awt.Color} object.
   *
   * @param color the fill color
   * @return this element for chaining
   */
  T fill(java.awt.Color color) {
    addAttribute('fill', String.format('#%02x%02x%02x', color.red, color.green, color.blue))
  }

  /**
   * Returns the fill paint.
   *
   * @return the fill value
   */
  String getFill() {
    getAttribute('fill')
  }

  /**
   * Sets the stroke paint.
   *
   * @param stroke the stroke color
   * @return this element for chaining
   */
  T stroke(String stroke) {
    addAttribute('stroke', stroke)
  }

  /**
   * Sets the stroke paint using a Color object.
   *
   * @param color the stroke color
   * @return this element for chaining
   */
  T stroke(Color color) {
    addAttribute('stroke', color.toString())
  }

  /**
   * Sets the stroke paint using a {@code java.awt.Color} object.
   *
   * @param color the stroke color
   * @return this element for chaining
   */
  T stroke(java.awt.Color color) {
    addAttribute('stroke', String.format('#%02x%02x%02x', color.red, color.green, color.blue))
  }

  /**
   * Returns the stroke paint.
   *
   * @return the stroke value
   */
  String getStroke() {
    getAttribute('stroke')
  }

  /**
   * Sets the stroke width.
   *
   * @param strokeWidth the stroke width
   * @return this element for chaining
   */
  T strokeWidth(Number strokeWidth) {
    addAttribute('stroke-width', strokeWidth)
  }

  /**
   * Sets the stroke width using a string value (for example {@code "2"} or {@code "1.5px"}).
   *
   * @param strokeWidth the stroke width
   * @return this element for chaining
   */
  T strokeWidth(String strokeWidth) {
    addAttribute('stroke-width', strokeWidth)
  }

  /**
   * Returns the stroke width.
   *
   * @return the stroke-width value
   */
  String getStrokeWidth() {
    getAttribute('stroke-width')
  }

  /**
   * Sets the stroke opacity (0 to 1).
   *
   * @param alpha the stroke opacity
   * @return this element for chaining
   */
  T strokeOpacity(Number alpha) {
    addAttribute('stroke-opacity', alpha)
  }

  /**
   * Sets the stroke opacity using a string value (for example {@code "0.5"} or {@code "50%"}).
   *
   * @param alpha the stroke opacity
   * @return this element for chaining
   */
  T strokeOpacity(String alpha) {
    addAttribute('stroke-opacity', alpha)
  }

  /**
   * Returns the stroke opacity.
   *
   * @return the stroke-opacity value
   */
  String getStrokeOpacity() {
    getAttribute('stroke-opacity')
  }

  /**
   * Sets the stroke dash pattern (for example {@code "4 2"}).
   *
   * @param dashArray the dash pattern
   * @return this element for chaining
   */
  T strokeDasharray(String dashArray) {
    addAttribute('stroke-dasharray', dashArray)
  }

  /**
   * Returns the stroke dash pattern.
   *
   * @return the stroke-dasharray value
   */
  String getStrokeDasharray() {
    getAttribute('stroke-dasharray')
  }

  /**
   * Sets the stroke line cap style (for example {@code butt}, {@code round}, or {@code square}).
   *
   * @param value the line cap style
   * @return this element for chaining
   */
  T strokeLinecap(String value) {
    addAttribute('stroke-linecap', value)
  }

  /**
   * Returns the stroke line cap style.
   *
   * @return the stroke-linecap value
   */
  String getStrokeLinecap() {
    getAttribute('stroke-linecap')
  }

  /**
   * Sets the stroke line join style (for example {@code miter}, {@code round}, or {@code bevel}).
   *
   * @param value the line join style
   * @return this element for chaining
   */
  T strokeLinejoin(String value) {
    addAttribute('stroke-linejoin', value)
  }

  /**
   * Returns the stroke line join style.
   *
   * @return the stroke-linejoin value
   */
  String getStrokeLinejoin() {
    getAttribute('stroke-linejoin')
  }

  /**
   * Sets the overall opacity (0 to 1) for the entire element.
   *
   * @param alpha the opacity value (0 = transparent, 1 = opaque)
   * @return this element for chaining
   */
  T opacity(Number alpha) {
    addAttribute('opacity', alpha)
  }

  /**
   * Sets the overall opacity using a string value (for example {@code "0.5"} or {@code "50%"}).
   *
   * @param alpha the opacity value
   * @return this element for chaining
   */
  T opacity(String alpha) {
    addAttribute('opacity', alpha)
  }

  /**
   * Returns the overall opacity.
   *
   * @return the opacity value
   */
  String getOpacity() {
    getAttribute('opacity')
  }

  /**
   * Sets the transform attribute directly as a string.
   *
   * @param value the transform value
   * @return this element for chaining
   */
  T transform(String value) {
    addAttribute('transform', value)
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
   * Appends a translate transform.
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
   * Appends a scale transform.
   *
   * @param sx the x-axis scale factor
   * @param sy the y-axis scale factor
   * @return this element for chaining
   */
  T scale(Number sx, Number sy) {
    appendTransform("scale($sx $sy)")
  }

  /**
   * Appends a uniform scale transform.
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
   * Appends a transform to the existing transform attribute.
   * Creates the attribute if it doesn't exist; appends with a space separator otherwise.
   *
   * @param transformValue the transform to append
   * @return this element for chaining
   */
  T appendTransform(String transformValue) {
    String existing = getAttribute('transform')
    if (existing == null || existing.isEmpty()) {
      addAttribute('transform', transformValue)
    } else {
      addAttribute('transform', "$existing $transformValue")
    }
    this as T
  }
}
