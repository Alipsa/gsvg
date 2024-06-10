package se.alipsa.groovy.svg

import org.dom4j.Document
import org.dom4j.DocumentHelper

/**
<svg width="${width}" height="${height}" xmlns="${xmlns}">
svgElements.each {
  svg.append(it.toXml()).append('\n')
}
</svg>
 */
class Svg extends AbstractElementContainer<Svg> implements GradientContainer, Animatable<Svg> {
  static final String NAME='svg'
  static final String xmlns="http://www.w3.org/2000/svg"

  Svg() {
    super(DocumentHelper.createDocument().addElement(NAME, "${xmlns}"))
  }

  Svg(AbstractElementContainer parent) {
    super(parent, NAME)
  }

  Svg(Number w, Number h) {
    this()
    width(w)
    height(h)
  }

  Svg(String w, String h) {
    this()
    width(w)
    height(h)
  }

  Svg width(String width) {
    addAttribute('width', width)
  }

  Svg width(Number width) {
    addAttribute('width', width)
  }

  String getWidth() {
    getAttribute('width')
  }

  Svg height(Number height) {
    addAttribute('height', height)
  }

  Svg height(String height) {
    addAttribute('height', height)
  }

  String getHeight() {
    getAttribute('height')
  }

  Document getDocument() {
    element.getDocument()
  }

  Defs addDefs() {
    add(new Defs(this))
  }

  Defs addDefs(String id) {
    addDefs().id(id)
  }

  Svg viewBox(String params) {
    addAttribute('viewBox', params)
  }

  String getViewBox() {
    getAttribute('viewBox')
  }

  Svg xlink() {
    element.addNamespace(xlinkNs.prefix, xlinkNs.getURI())
    this
  }

  // This is the top element so we return itself as the parent
  @Override
  SvgElement getParent() {
    this
  }

  Svg version(String version) {
    addAttribute('version', version)
  }

  String getVersion() {
    getAttribute('version')
  }

  ForeignObject addForeignObject() {
    add(new ForeignObject(this))
  }

  Style addStyle() {
    add(new Style(this))
  }

  Metadata addMetadata() {
    add(new Metadata(this))
  }

  Script addScript() {
    add(new Script(this))
  }

  Switch addSwitch() {
    add(new Switch(this))
  }

  Symbol addSymbol() {
    add(new Symbol(this))
  }

  Symbol addSymbol(String id) {
    addSymbol().id(id)
  }

  View addView() {
    add(new View(this))
  }

  View addView(String id) {
    addView().id(id)
  }

}
