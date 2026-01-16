package se.alipsa.groovy.svg



import groovy.transform.PackageScope
import org.dom4j.Element
import groovy.transform.CompileStatic

/**
 * SVG {@code <filter>} element that defines a filter effects region and pipeline.
 */
@CompileStatic
class Filter extends AbstractElementContainer<Filter> {

  static final String NAME = 'filter'

  /**
   * Creates a Filter.
   *
   * @param parent the parent SVG element
   */
  Filter(SvgElement<? extends SvgElement> parent) {
    super(parent, NAME)
  }

  @PackageScope
  Filter(SvgElement parent, Element element) {
    super(parent, element)
  }


  /**
   * Sets the x attribute.
   *
   * @param x the x-coordinate
   * @return this element for chaining
   */
  Filter x(Number x) {
    addAttribute('x', x)
  }

  /**
   * Sets the x attribute.
   *
   * @param x the x-coordinate
   * @return this element for chaining
   */
  Filter x(String x) {
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
  Filter y(Number y) {
    addAttribute('y', y)
  }

  /**
   * Sets the y attribute.
   *
   * @param y the y-coordinate
   * @return this element for chaining
   */
  Filter y(String y) {
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
   * Sets the filter units attribute.
   *
   * @param units the coordinate system units
   * @return this element for chaining
   */
  Filter filterUnits(String units) {
    addAttribute('filterUnits', units)
  }

  /**
   * Returns the filter units value.
   *
   * @return the filter units value
   */
  String getFilterUnits() {
    getAttribute('filterUnits')
  }

  /**
   * Sets the width attribute.
   *
   * @param width the width
   * @return this element for chaining
   */
  Filter width(Number width) {
    addAttribute('width', width)
  }

  /**
   * Sets the width attribute.
   *
   * @param width the width
   * @return this element for chaining
   */
  Filter width(String width) {
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
  Filter height(Number height) {
    addAttribute('height', height)
  }

  /**
   * Sets the height attribute.
   *
   * @param height the height
   * @return this element for chaining
   */
  Filter height(String height) {
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
   * Sets the color interpolation filters attribute.
   *
   * @param value the value
   * @return this element for chaining
   */
  Filter colorInterpolationFilters(String value) {
    addAttribute('color-interpolation-filters', value)
    this
  }

  /**
   * Returns the color interpolation filters value.
   *
   * @return the color interpolation filters value
   */
  String getColorInterpolationFilters() {
    getAttribute('color-interpolation-filters')
  }

  /**
   * Sets the filter res attribute.
   *
   * @param res the filter resolution (sampling rate)
   * @return this element for chaining
   */
  Filter filterRes(String res) {
    addAttribute('filterRes', res)
    this
  }

  /**
   * Returns the filter res value.
   *
   * @return the filter res value
   */
  String getFilterRes() {
    getAttribute('filterRes')
  }

  /**
   * Creates and adds a new FeGaussianBlur child element.
   *
   * @return the created element
   */
  FeGaussianBlur addFeGaussianBlur() {
    add(new FeGaussianBlur(this))
  }

  FeGaussianBlur addFeGaussianBlur(Map attributes) {
    FeGaussianBlur elem = addFeGaussianBlur()
    attributes.each {
      key, value -> elem.addAttribute(String.valueOf(key), value)
    }
    elem
  }

  /**
   * Creates and adds a new FeBlend child element.
   *
   * @return the created element
   */
  FeBlend addFeBlend() {
    add(new FeBlend(this))
  }

  FeBlend addFeBlend(Map attributes) {
    FeBlend elem = addFeBlend()
    attributes.each {
      key, value -> elem.addAttribute(String.valueOf(key), value)
    }
    elem
  }

  /**
   * Creates and adds a new FeFlood child element.
   *
   * @return the created element
   */
  FeFlood addFeFlood() {
    add(new FeFlood(this))
  }

  FeFlood addFeFlood(Map attributes) {
    FeFlood elem = addFeFlood()
    attributes.each {
      key, value -> elem.addAttribute(String.valueOf(key), value)
    }
    elem
  }

  /**
   * Creates and adds a new FeColorMatrix child element.
   *
   * @return the created element
   */
  FeColorMatrix addFeColorMatrix() {
    add(new FeColorMatrix(this))
  }

  FeColorMatrix addFeColorMatrix(Map attributes) {
    FeColorMatrix elem = addFeColorMatrix()
    attributes.each {
      key, value -> elem.addAttribute(String.valueOf(key), value)
    }
    elem
  }

  /**
   * Creates and adds a new FeComponentTransfer child element.
   *
   * @return the created element
   */
  FeComponentTransfer addFeComponentTransfer() {
    add(new FeComponentTransfer(this))
  }

  FeComponentTransfer addFeComponentTransfer(Map attributes) {
    FeComponentTransfer elem = addFeComponentTransfer()
    attributes.each {
      key, value -> elem.addAttribute(String.valueOf(key), value)
    }
    elem
  }

  /**
   * Creates and adds a new FeImage child element.
   *
   * @return the created element
   */
  FeImage addFeImage() {
    add(new FeImage(this))
  }

  FeImage addFeImage(Map attributes) {
    FeImage elem = addFeImage()
    attributes.each {
      key, value -> elem.addAttribute(String.valueOf(key), value)
    }
    elem
  }

  /**
   * Creates and adds a new FeComposite child element.
   *
   * @return the created element
   */
  FeComposite addFeComposite() {
    add(new FeComposite(this))
  }

  FeComposite addFeComposite(Map attributes) {
    FeComposite elem = addFeComposite()
    attributes.each {
      key, value -> elem.addAttribute(String.valueOf(key), value)
    }
    elem
  }

  /**
   * Creates and adds a new FeConvolveMatrix child element.
   *
   * @return the created element
   */
  FeConvolveMatrix addFeConvolveMatrix() {
    add(new FeConvolveMatrix(this))
  }

  FeConvolveMatrix addFeConvolveMatrix(Map attributes) {
    FeConvolveMatrix elem = addFeConvolveMatrix()
    attributes.each {
      key, value -> elem.addAttribute(String.valueOf(key), value)
    }
    elem
  }

  /**
   * Creates and adds a new FeDiffuseLighting child element.
   *
   * @return the created element
   */
  FeDiffuseLighting addFeDiffuseLighting() {
    add(new FeDiffuseLighting(this))
  }

  FeDiffuseLighting addFeDiffuseLighting(Map attributes) {
    FeDiffuseLighting elem = addFeDiffuseLighting()
    attributes.each {
      key, value -> elem.addAttribute(String.valueOf(key), value)
    }
    elem
  }

  /**
   * Creates and adds a new FeMerge child element.
   *
   * @return the created element
   */
  FeMerge addFeMerge() {
    add(new FeMerge(this))
  }

  /**
   * Creates and adds a new FeMerge child element.
   *
   * @param id the unique identifier
   * @return the created element
   */
  FeMerge addFeMerge(String id) {
    addFeMerge().id(id)
  }

  FeMerge addFeMerge(Map attributes) {
    FeMerge elem = addFeMerge()
    attributes.each {
      key, value -> elem.addAttribute(String.valueOf(key), value)
    }
    elem
  }

  /**
   * Creates and adds a new FeOffset child element.
   *
   * @return the created element
   */
  FeOffset addFeOffset() {
    add(new FeOffset(this))
  }

  FeOffset addFeOffset(Map attributes) {
    FeOffset elem = addFeOffset()
    attributes.each {
      key, value -> elem.addAttribute(String.valueOf(key), value)
    }
    elem
  }

  /**
   * Creates and adds a new FeTile child element.
   *
   * @return the created element
   */
  FeTile addFeTile() {
    add(new FeTile(this))
  }

  FeTile addFeTile(Map attributes) {
    FeTile elem = addFeTile()
    attributes.each {
      key, value -> elem.addAttribute(String.valueOf(key), value)
    }
    elem
  }

  /**
   * Creates and adds a new FeDisplacementMap child element.
   *
   * @return the created element
   */
  FeDisplacementMap addFeDisplacementMap() {
    add(new FeDisplacementMap(this))
  }

  FeDisplacementMap addFeDisplacementMap(Map attributes) {
    FeDisplacementMap elem = addFeDisplacementMap()
    attributes.each {
      key, value -> elem.addAttribute(String.valueOf(key), value)
    }
    elem
  }

  /**
   * Creates and adds a new FeTurbulence child element.
   *
   * @return the created element
   */
  FeTurbulence addFeTurbulence() {
    add(new FeTurbulence(this))
  }

  FeTurbulence addFeTurbulence(Map attributes) {
    FeTurbulence elem = addFeTurbulence()
    attributes.each {
      key, value -> elem.addAttribute(String.valueOf(key), value)
    }
    elem
  }

  /**
   * Creates and adds a new FeDropShadow child element.
   *
   * @return the created element
   */
  FeDropShadow addFeDropShadow() {
    add(new FeDropShadow(this))
  }

  FeDropShadow addFeDropShadow(Map attributes) {
    FeDropShadow elem = addFeDropShadow()
    attributes.each {
      key, value -> elem.addAttribute(String.valueOf(key), value)
    }
    elem
  }

  /**
   * Creates and adds a new FeMorphology child element.
   *
   * @return the created element
   */
  FeMorphology addFeMorphology() {
    add(new FeMorphology(this))
  }

  FeMorphology addFeMorphology(Map attributes) {
    FeMorphology elem = addFeMorphology()
    attributes.each {
      key, value -> elem.addAttribute(String.valueOf(key), value)
    }
    elem
  }

  /**
   * Creates and adds a new FeSpecularLighting child element.
   *
   * @return the created element
   */
  FeSpecularLighting addFeSpecularLighting() {
    add(new FeSpecularLighting(this))
  }

  FeSpecularLighting addFeSpecularLighting(Map attributes) {
    FeSpecularLighting elem = addFeSpecularLighting()
    attributes.each {
      key, value -> elem.addAttribute(String.valueOf(key), value)
    }
    elem
  }
}