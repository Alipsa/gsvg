package se.alipsa.groovy.svg

import groovy.transform.CompileStatic
import org.dom4j.Element

/**
 * SVG {@code <image>} element that embeds a raster image.
 */
@CompileStatic
class Image extends SvgElement<Image> implements Animatable<Image> {

  static final String NAME='image'

  /**
   * Creates a Image.
   *
   * @param parent the parent SVG element
   */
  Image(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  /**
   * Creates a Image by adopting an existing DOM4J Element.
   * Used for cloning/copying operations.
   *
   * @param parent the parent SVG element
   * @param element the DOM4J element to adopt
   */
  Image(SvgElement parent, Element element) {
    super(parent, element)
  }

  /**
   * Sets the width attribute.
   *
   * @param width the width
   * @return this element for chaining
   */
  Image width(Number width) {
    addAttribute('width', width)
  }

  /**
   * Sets the width attribute.
   *
   * @param width the width
   * @return this element for chaining
   */
  Image width(String width) {
    addAttribute('width', width)
  }

  /**
   * Returns the width value.
   *
   * @return the width value
   */
  String getWidth() {
    getAttribute('width')
  }

  /**
   * Sets the height attribute.
   *
   * @param height the height
   * @return this element for chaining
   */
  Image height(Number height) {
    addAttribute('height', height)
  }

  /**
   * Sets the height attribute.
   *
   * @param height the height
   * @return this element for chaining
   */
  Image height(String height) {
    addAttribute('height', height)
  }

  /**
   * Returns the height value.
   *
   * @return the height value
   */
  String getHeight() {
    getAttribute('height')
  }

  /**
   * Sets the x attribute.
   *
   * @param x the x-coordinate
   * @return this element for chaining
   */
  Image x(Number x) {
    addAttribute('x', x)
  }

  /**
   * Sets the x attribute.
   *
   * @param x the x-coordinate
   * @return this element for chaining
   */
  Image x(String x) {
    addAttribute('x', x)
  }

  /**
   * Returns the x value.
   *
   * @return the x value
   */
  String getX() {
    getAttribute('x')
  }

  /**
   * Sets the y attribute.
   *
   * @param y the y-coordinate
   * @return this element for chaining
   */
  Image y(Number y) {
    addAttribute('y', y)
  }

  /**
   * Sets the y attribute.
   *
   * @param y the y-coordinate
   * @return this element for chaining
   */
  Image y(String y) {
    addAttribute('y', y)
  }

  /**
   * Returns the y value.
   *
   * @return the y value
   */
  String getY() {
    getAttribute('y')
  }

  /**
   * Sets the xlink href attribute.
   *
   * @param href the hyperlink URL
   * @return this element for chaining
   */
  Image xlinkHref(String href) {
    addAttribute(xlink('href'), href)
  }

  /**
   * Sets the href attribute.
   *
   * @param href the hyperlink URL
   * @return this element for chaining
   */
  Image href(String href) {
    addAttribute('href', href)
  }

  /**
   * Returns the href value.
   *
   * @return the href value
   */
  String getHref() {
    getAttribute('href') ?: getAttribute(xlink('href'))
  }

  /**
   * Adds an attribute to this element.
   *
   * @param name the name of the element
   * @param value the value
   * @return the created element
   */
  @Override
  Image addAttribute(String name, Object value) {
    if ('href' == name) {
      element.addAttribute('href', "$value")
      return this
    }
    if ('xlink:href' == name) {
      element.addAttribute(xlink('href'), "$value")
      return this
    }
    super.addAttribute(name, value) as Image
  }

  /**
   * <align> [<scaleMethod>]
   * align is one of none, xMinYMin, xMidYMin, xMaxYMin, xMinYMid, xMidYMid, xMaxYMid, xMinYMax, xMidYMax, xMaxYMax
   * scaleMethod is an optional argument that have the value "meet" or "slice"
   *
   * @param value the alignment followed by an (optional) scaleMethod
   * @return this image
   */
  Image preserveAspectRatio(String value) {
    addAttribute('preserveAspectRatio', value)
  }

  /**
   * Returns the preserve aspect ratio value.
   *
   * @return the preserve aspect ratio value
   */
  String getPreserveAspectRatio() {
    getAttribute('preserveAspectRatio')
  }

  /**
   *
   * @param value one of "anonymous", "use-credentials", ""
   * @return this image
   */
  Image crossorigin(String value) {
    addAttribute('crossorigin', value)
  }

  /**
   * Returns the crossorigin value.
   *
   * @return the crossorigin value
   */
  String getCrossorigin() {
    getAttribute('crossorigin')
  }

  /**
   *
   * @param value one of "sync", "async", "auto" (default)
   * @return this image
   */
  Image decoding(String value) {
    addAttribute('decoding', value)
  }
}
