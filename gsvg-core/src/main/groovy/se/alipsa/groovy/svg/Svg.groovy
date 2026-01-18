package se.alipsa.groovy.svg



import groovy.transform.PackageScope
import org.dom4j.Element
import groovy.transform.CompileStatic
import org.dom4j.Document
import org.dom4j.DocumentHelper
import se.alipsa.groovy.svg.utils.ViewBox

/**
 * Root SVG document element and entry point for building or parsing SVG content.
 */
@CompileStatic
class Svg extends AbstractElementContainer<Svg> implements GradientContainer, Animatable<Svg> {
  static final String NAME='svg'
  static final String xmlns="http://www.w3.org/2000/svg"

  /**
   * Optional document-level numeric precision override.
   * When set, all numeric attributes in this document use this precision.
   * When null, the global default precision is used.
   */
  private Integer documentPrecision = null

  /**
   * Creates a Svg.
   */
  Svg() {
    super(DocumentHelper.createDocument().addElement(NAME, "${xmlns}"))
  }

  /**
   * Creates a Svg.
   *
   * @param parent the parent SVG element
   */
  Svg(AbstractElementContainer parent) {
    super(parent, NAME)
  }

  /**
   * Creates a Svg.
   *
   * @param w the width
   * @param h the height
   */
  Svg(Number w, Number h) {
    this()
    width(w)
    height(h)
  }

  /**
   * Creates a Svg.
   *
   * @param w the width
   * @param h the height
   */
  Svg(String w, String h) {
    this()
    width(w)
    height(h)
  }

  @PackageScope
  Svg(SvgElement parent, Element element) {
    super(parent, element)
  }

  /**
   * Sets the numeric precision for this SVG document.
   * <p>
   * Controls how many decimal places are used when formatting numeric
   * attributes (coordinates, dimensions, etc.). This affects all elements
   * in this document.
   * <p>
   * Setting precision to lower values reduces file size. The default (3 decimals)
   * matches industry standards and provides good balance between file size and
   * visual quality.
   * <p>
   * Example:
   * <pre>
   * def svg = new Svg(200, 200).setMaxPrecision(2)
   * svg.addCircle().cx(12.123456)  // Outputs cx="12.12" instead of cx="12.123"
   * </pre>
   *
   * @param decimals maximum decimal places (0-10)
   * @return this for chaining
   * @throws IllegalArgumentException if decimals is outside valid range
   */
  Svg setMaxPrecision(int decimals) {
    if (decimals < 0 || decimals > 10) {
      throw new IllegalArgumentException("Precision must be between 0 and 10, got: ${decimals}")
    }
    this.documentPrecision = decimals
    this
  }

  /**
   * Gets the current numeric precision setting for this document.
   *
   * @return the precision setting, or null if using global default
   */
  Integer getMaxPrecision() {
    documentPrecision
  }

  /**
   * Gets the effective precision for this document.
   * <p>
   * Package-private method used by SvgElement to determine precision.
   *
   * @return the document precision, or null to use global default
   */
  @PackageScope
  Integer getEffectivePrecision() {
    documentPrecision
  }

  /**
   * Sets the width attribute.
   *
   * @param width the width
   * @return this element for chaining
   */
  Svg width(String width) {
    addAttribute('width', width)
  }

  /**
   * Sets the width attribute.
   *
   * @param width the width
   * @return this element for chaining
   */
  Svg width(Number width) {
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
  Svg height(Number height) {
    addAttribute('height', height)
  }

  /**
   * Sets the height attribute.
   *
   * @param height the height
   * @return this element for chaining
   */
  Svg height(String height) {
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
   * Returns the document value.
   *
   * @return the document value
   */
  Document getDocument() {
    element.getDocument()
  }

  /**
   * Creates and adds a new Defs child element.
   *
   * @return the created element
   */
  Defs addDefs() {
    add(new Defs(this))
  }

  /**
   * Creates and adds a new Defs child element.
   *
   * @param id the unique identifier
   * @return the created element
   */
  Defs addDefs(String id) {
    addDefs().id(id)
  }

  /**
   * Creates and adds a new Defs child element with attributes.
   *
   * @param attributes map of attributes to apply
   * @return the created element
   */
  Defs addDefs(Map attributes) {
    Defs defs = addDefs()
    attributes.each {
      key, value -> defs.addAttribute(String.valueOf(key), value)
    }
    defs
  }

  /**
   * Sets the view box attribute.
   *
   * @param params the parameters
   * @return this element for chaining
   */
  Svg viewBox(String params) {
    addAttribute('viewBox', params)
  }

  /**
   * Returns the view box value.
   *
   * @return the view box value
   */
  String getViewBox() {
    getAttribute('viewBox')
  }

  /**
   * Sets the view box from a ViewBox object.
   * <p>Example:</p>
   * <pre>
   * svg.viewBox(ViewBox.of(0, 0, 100, 100))
   * svg.viewBox(ViewBox.parse("0 0 100 100").scale(2))
   * </pre>
   *
   * @param viewBox the ViewBox object
   * @return this element for chaining
   */
  Svg viewBox(ViewBox viewBox) {
    addAttribute('viewBox', viewBox.toString())
  }

  /**
   * Sets the view box with four numeric parameters (convenience method).
   * <p>Example:</p>
   * <pre>
   * svg.viewBox(0, 0, 100, 100)
   * </pre>
   *
   * @param minX the minimum x coordinate
   * @param minY the minimum y coordinate
   * @param width the viewport width
   * @param height the viewport height
   * @return this element for chaining
   */
  Svg viewBox(Number minX, Number minY, Number width, Number height) {
    viewBox(ViewBox.of(minX, minY, width, height))
  }

  /**
   * Parses and returns the current viewBox as a ViewBox object.
   * @return the parsed ViewBox, or null if not set
   */
  ViewBox getViewBoxObject() {
    String vb = getViewBox()
    vb ? ViewBox.parse(vb) : null
  }

  /**
   * Adds the xlink namespace declaration.
   *
   * @return this element for chaining
   */
  Svg xlink() {
    element.addNamespace(xlinkNs.prefix, xlinkNs.getURI())
    this
  }

  // This is the top element so we return itself as the parent
  /**
   * Returns the parent value.
   *
   * @return the parent value
   */
  @Override
  SvgElement getParent() {
    this
  }

  /**
   * Sets the version attribute.
   *
   * @param version the version
   * @return this element for chaining
   */
  Svg version(String version) {
    addAttribute('version', version)
  }

  /**
   * Returns the version value.
   *
   * @return the version value
   */
  String getVersion() {
    getAttribute('version')
  }

  /**
   * Creates and adds a new ForeignObject child element.
   *
   * @return the created element
   */
  ForeignObject addForeignObject() {
    add(new ForeignObject(this))
  }

  /**
   * Creates and adds a new ForeignObject child element with attributes.
   *
   * @param attributes map of attributes to apply
   * @return the created element
   */
  ForeignObject addForeignObject(Map attributes) {
    ForeignObject foreignObject = addForeignObject()
    attributes.each {
      key, value -> foreignObject.addAttribute(String.valueOf(key), value)
    }
    foreignObject
  }

  /**
   * Creates and adds a new Style child element.
   *
   * @return the created element
   */
  Style addStyle() {
    add(new Style(this))
  }

  /**
   * Creates and adds a new Metadata child element.
   *
   * @return the created element
   */
  Metadata addMetadata() {
    add(new Metadata(this))
  }

  /**
   * Creates and adds a new Metadata child element with attributes.
   *
   * @param attributes map of attributes to apply
   * @return the created element
   */
  Metadata addMetadata(Map attributes) {
    Metadata metadata = addMetadata()
    attributes.each { key, value ->
      metadata.addAttribute(String.valueOf(key), value)
    }
    metadata
  }

  /**
   * Creates and adds a new Script child element.
   *
   * @return the created element
   */
  Script addScript() {
    add(new Script(this))
  }

  /**
   * Creates and adds a new Script child element with attributes.
   *
   * @param attributes map of attributes to apply
   * @return the created element
   */
  Script addScript(Map attributes) {
    Script script = addScript()
    attributes.each { key, value ->
      script.addAttribute(String.valueOf(key), value)
    }
    script
  }

  /**
   * Creates and adds a new Switch child element.
   *
   * @return the created element
   */
  Switch addSwitch() {
    add(new Switch(this))
  }

  /**
   * Creates and adds a new Switch child element with attributes.
   *
   * @param attributes map of attributes to apply
   * @return the created element
   */
  Switch addSwitch(Map attributes) {
    Switch sw = addSwitch()
    attributes.each { key, value ->
      sw.addAttribute(String.valueOf(key), value)
    }
    sw
  }

  /**
   * Creates and adds a new Symbol child element.
   *
   * @return the created element
   */
  Symbol addSymbol() {
    add(new Symbol(this))
  }

  /**
   * Creates and adds a new Symbol child element with attributes.
   *
   * @param attributes map of attributes to apply
   * @return the created element
   */
  Symbol addSymbol(Map attributes) {
    Symbol symbol = addSymbol()
    attributes.each { key, value ->
      symbol.addAttribute(String.valueOf(key), value)
    }
    symbol
  }

  /**
   * Creates and adds a new Symbol child element.
   *
   * @param id the unique identifier
   * @return the created element
   */
  Symbol addSymbol(String id) {
    addSymbol().id(id)
  }

  /**
   * Creates and adds a new View child element.
   *
   * @return the created element
   */
  View addView() {
    add(new View(this))
  }

  /**
   * Creates and adds a new View child element.
   *
   * @param id the unique identifier
   * @return the created element
   */
  View addView(String id) {
    addView().id(id)
  }

  /**
   * Creates and adds a new View child element with attributes.
   *
   * @param attributes map of attributes to apply
   * @return the created element
   */
  View addView(Map attributes) {
    View view = addView()
    attributes.each { key, value ->
      view.addAttribute(String.valueOf(key), value)
    }
    view
  }

  /**
   * Validates this SVG document using default validation rules.
   * <p>
   * Applies all default validation rules to the entire document tree and returns
   * a report with any issues found. Validation is opt-in and permissive - the SVG
   * is still valid even if validation reports issues.
   * <p>
   * Example:
   * <pre>
   * Svg svg = new Svg(100, 100)
   * ValidationReport report = svg.validate()
   * if (report.hasErrors()) {
   *     println "Validation failed: ${report.errorCount} errors"
   *     report.errors.each { println it }
   * }
   * </pre>
   *
   * @return validation report with all issues found
   * @see se.alipsa.groovy.svg.validation.ValidationEngine
   */
  se.alipsa.groovy.svg.validation.ValidationReport validate() {
    validate(se.alipsa.groovy.svg.validation.ValidationEngine.createDefault())
  }

  /**
   * Validates this SVG document using a custom validation engine.
   * <p>
   * Allows customizing which validation rules are applied by providing
   * your own configured {@link se.alipsa.groovy.svg.validation.ValidationEngine}.
   * <p>
   * Example:
   * <pre>
   * ValidationEngine engine = ValidationEngine.createDefault()
   * engine.removeRule("VIEWBOX_RULE")  // Disable viewBox validation
   * ValidationReport report = svg.validate(engine)
   * </pre>
   *
   * @param engine the validation engine to use
   * @return validation report with all issues found
   */
  se.alipsa.groovy.svg.validation.ValidationReport validate(se.alipsa.groovy.svg.validation.ValidationEngine engine) {
    engine.validate(this)
  }

  /**
   * Checks if this SVG document passes validation (no errors).
   * <p>
   * This is a convenience method equivalent to {@code validate().isValid()}.
   * Note that warnings and info messages don't prevent validation from passing.
   *
   * @return true if validation passes (no errors)
   */
  boolean isValid() {
    validate().isValid()
  }

  // ========== Shape Factory Methods ==========

  /**
   * Creates a rounded rectangle with uniform corner radius.
   * <p>
   * Convenience method that delegates to {@link se.alipsa.groovy.svg.presets.Shapes#roundedRect}.
   * <p>
   * Options:
   * <ul>
   *   <li>x - X coordinate (default: 0)</li>
   *   <li>y - Y coordinate (default: 0)</li>
   *   <li>width - Rectangle width (required)</li>
   *   <li>height - Rectangle height (required)</li>
   *   <li>radius - Corner radius (default: 5)</li>
   * </ul>
   * <p>
   * Example:
   * <pre>
   * Rect rounded = svg.createRoundedRect(x: 10, y: 10, width: 100, height: 60, radius: 10)
   * </pre>
   *
   * @param options configuration map
   * @return configured Rect element
   */
  Rect createRoundedRect(Map options) {
    se.alipsa.groovy.svg.presets.Shapes.roundedRect(this, options)
  }

  /**
   * Creates a star polygon with specified number of points.
   * <p>
   * Convenience method that delegates to {@link se.alipsa.groovy.svg.presets.Shapes#star}.
   * <p>
   * Options:
   * <ul>
   *   <li>cx - Center X coordinate (default: 0)</li>
   *   <li>cy - Center Y coordinate (default: 0)</li>
   *   <li>points - Number of star points (default: 5)</li>
   *   <li>outerRadius - Radius to outer points (default: 50)</li>
   *   <li>innerRadius - Radius to inner points (default: outerRadius / 2.5)</li>
   * </ul>
   * <p>
   * Example:
   * <pre>
   * Polygon star = svg.createStar(cx: 100, cy: 100, points: 5, outerRadius: 50, innerRadius: 25)
   * </pre>
   *
   * @param options configuration map
   * @return configured Polygon element
   */
  Polygon createStar(Map options) {
    se.alipsa.groovy.svg.presets.Shapes.star(this, options)
  }

  /**
   * Creates an arrow path from point (x1,y1) to (x2,y2).
   * <p>
   * Convenience method that delegates to {@link se.alipsa.groovy.svg.presets.Shapes#arrow}.
   * <p>
   * Options:
   * <ul>
   *   <li>x1 - Start X coordinate (required)</li>
   *   <li>y1 - Start Y coordinate (required)</li>
   *   <li>x2 - End X coordinate (required)</li>
   *   <li>y2 - End Y coordinate (required)</li>
   *   <li>headSize - Arrow head size in pixels (default: 10)</li>
   *   <li>headAngle - Arrow head angle in degrees (default: 30)</li>
   * </ul>
   * <p>
   * Example:
   * <pre>
   * Path arrow = svg.createArrow(x1: 10, y1: 50, x2: 100, y2: 50, headSize: 15)
   * </pre>
   *
   * @param options configuration map
   * @return configured Path element
   */
  Path createArrow(Map options) {
    se.alipsa.groovy.svg.presets.Shapes.arrow(this, options)
  }

  /**
   * Creates a regular polygon (triangle, hexagon, etc).
   * <p>
   * Convenience method that delegates to {@link se.alipsa.groovy.svg.presets.Shapes#regularPolygon}.
   * <p>
   * Options:
   * <ul>
   *   <li>cx - Center X coordinate (default: 0)</li>
   *   <li>cy - Center Y coordinate (default: 0)</li>
   *   <li>sides - Number of sides (default: 6)</li>
   *   <li>radius - Distance from center to vertices (default: 50)</li>
   *   <li>rotation - Rotation angle in degrees (default: 0)</li>
   * </ul>
   * <p>
   * Example:
   * <pre>
   * Polygon hexagon = svg.createRegularPolygon(cx: 100, cy: 100, sides: 6, radius: 50)
   * Polygon triangle = svg.createRegularPolygon(cx: 50, cy: 50, sides: 3, radius: 30)
   * </pre>
   *
   * @param options configuration map
   * @return configured Polygon element
   */
  Polygon createRegularPolygon(Map options) {
    se.alipsa.groovy.svg.presets.Shapes.regularPolygon(this, options)
  }

  /**
   * Creates a speech bubble with a tail pointing to a specific location.
   * <p>
   * Convenience method that delegates to {@link se.alipsa.groovy.svg.presets.Shapes#speechBubble}.
   * <p>
   * Options:
   * <ul>
   *   <li>x - Bubble X coordinate (default: 0)</li>
   *   <li>y - Bubble Y coordinate (default: 0)</li>
   *   <li>width - Bubble width (default: 100)</li>
   *   <li>height - Bubble height (default: 60)</li>
   *   <li>radius - Corner radius (default: 10)</li>
   *   <li>tailX - Tail point X coordinate (required)</li>
   *   <li>tailY - Tail point Y coordinate (required)</li>
   *   <li>tailWidth - Width of tail base (default: 20)</li>
   * </ul>
   * <p>
   * Example:
   * <pre>
   * Path bubble = svg.createSpeechBubble(x: 10, y: 10, width: 100, height: 60, tailX: 50, tailY: 80)
   * </pre>
   *
   * @param options configuration map
   * @return configured Path element
   */
  Path createSpeechBubble(Map options) {
    se.alipsa.groovy.svg.presets.Shapes.speechBubble(this, options)
  }

}
