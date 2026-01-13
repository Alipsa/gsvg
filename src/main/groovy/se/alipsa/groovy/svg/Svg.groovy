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

  View addView(Map attributes) {
    View view = addView()
    attributes.each { key, value ->
      view.addAttribute(String.valueOf(key), value)
    }
    view
  }

}