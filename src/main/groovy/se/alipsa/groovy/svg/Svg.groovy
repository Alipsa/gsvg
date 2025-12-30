package se.alipsa.groovy.svg

import org.dom4j.Document
import org.dom4j.DocumentHelper

/**
 * Root SVG document element and entry point for building or parsing SVG content.
 */
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
   * @param parent value
   */
  Svg(AbstractElementContainer parent) {
    super(parent, NAME)
  }

  /**
   * Creates a Svg.
   *
   * @param w value
   * @param h value
   */
  Svg(Number w, Number h) {
    this()
    width(w)
    height(h)
  }

  /**
   * Creates a Svg.
   *
   * @param w value
   * @param h value
   */
  Svg(String w, String h) {
    this()
    width(w)
    height(h)
  }

  /**
   * Sets the width attribute.
   *
   * @param width value
   * @return this element for chaining
   */
  Svg width(String width) {
    addAttribute('width', width)
  }

  /**
   * Sets the width attribute.
   *
   * @param width value
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
   * @param height value
   * @return this element for chaining
   */
  Svg height(Number height) {
    addAttribute('height', height)
  }

  /**
   * Sets the height attribute.
   *
   * @param height value
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
   * @param id value
   * @return the created element
   */
  Defs addDefs(String id) {
    addDefs().id(id)
  }

  /**
   * Sets the view box attribute.
   *
   * @param params value
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
   * @param version value
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
   * Creates and adds a new Script child element.
   *
   * @return the created element
   */
  Script addScript() {
    add(new Script(this))
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
   * Creates and adds a new Symbol child element.
   *
   * @return the created element
   */
  Symbol addSymbol() {
    add(new Symbol(this))
  }

  /**
   * Creates and adds a new Symbol child element.
   *
   * @param id value
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
   * @param id value
   * @return the created element
   */
  View addView(String id) {
    addView().id(id)
  }

}
