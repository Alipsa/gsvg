package se.alipsa.groovy.svg

/**
 * SVG {@code <filter>} element that defines a filter effects region and pipeline.
 */
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
   * @param res the result
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

  /**
   * Creates and adds a new FeBlend child element.
   *
   * @return the created element
   */
  FeBlend addFeBlend() {
    add(new FeBlend(this))
  }

  /**
   * Creates and adds a new FeFlood child element.
   *
   * @return the created element
   */
  FeFlood addFeFlood() {
    add(new FeFlood(this))
  }

  /**
   * Creates and adds a new FeColorMatrix child element.
   *
   * @return the created element
   */
  FeColorMatrix addFeColorMatrix() {
    add(new FeColorMatrix(this))
  }

  /**
   * Creates and adds a new FeComponentTransfer child element.
   *
   * @return the created element
   */
  FeComponentTransfer addFeComponentTransfer() {
    add(new FeComponentTransfer(this))
  }

  /**
   * Creates and adds a new FeImage child element.
   *
   * @return the created element
   */
  FeImage addFeImage() {
    add(new FeImage(this))
  }

  /**
   * Creates and adds a new FeComposite child element.
   *
   * @return the created element
   */
  FeComposite addFeComposite() {
    add(new FeComposite(this))
  }

  /**
   * Creates and adds a new FeConvolveMatrix child element.
   *
   * @return the created element
   */
  FeConvolveMatrix addFeConvolveMatrix() {
    add(new FeConvolveMatrix(this))
  }

  /**
   * Creates and adds a new FeDiffuseLighting child element.
   *
   * @return the created element
   */
  FeDiffuseLighting addFeDiffuseLighting() {
    add(new FeDiffuseLighting(this))
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

  /**
   * Creates and adds a new FeOffset child element.
   *
   * @return the created element
   */
  FeOffset addFeOffset() {
    add(new FeOffset(this))
  }

  /**
   * Creates and adds a new FeTile child element.
   *
   * @return the created element
   */
  FeTile addFeTile() {
    add(new FeTile(this))
  }

  /**
   * Creates and adds a new FeDisplacementMap child element.
   *
   * @return the created element
   */
  FeDisplacementMap addFeDisplacementMap() {
    add(new FeDisplacementMap(this))
  }

  /**
   * Creates and adds a new FeTurbulence child element.
   *
   * @return the created element
   */
  FeTurbulence addFeTurbulence() {
    add(new FeTurbulence(this))
  }

  /**
   * Creates and adds a new FeDropShadow child element.
   *
   * @return the created element
   */
  FeDropShadow addFeDropShadow() {
    add(new FeDropShadow(this))
  }

  /**
   * Creates and adds a new FeMorphology child element.
   *
   * @return the created element
   */
  FeMorphology addFeMorphology() {
    add(new FeMorphology(this))
  }

  /**
   * Creates and adds a new FeSpecularLighting child element.
   *
   * @return the created element
   */
  FeSpecularLighting addFeSpecularLighting() {
    add(new FeSpecularLighting(this))
  }
}
