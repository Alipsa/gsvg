package se.alipsa.groovy.svg

import groovy.transform.CompileStatic
import org.dom4j.QName

/**
 * Base class for SVG elements that contain text or CDATA content.
 */
@CompileStatic
abstract class StringContentContainer<T extends SvgElement<T>> extends SvgElement<T> {


  /**
   * Creates a StringContentContainer.
   *
   * @param parent the parent SVG element
   * @param name the name of the element
   */
  StringContentContainer(SvgElement<? extends SvgElement> parent, String name) {
    super(parent, name)
  }

  /**
   * Creates a StringContentContainer.
   *
   * @param parent the parent SVG element
   * @param qName the qualified name of the element
   */
  StringContentContainer(SvgElement<? extends SvgElement> parent, QName qName) {
    super(parent, qName)
  }

  /**
   * Creates a StringContentContainer.
   *
   * @param parent the parent SVG element
   * @param qName the qualified name of the element
   * @param defaultNameSpace the default namespace
   */
  StringContentContainer(SvgElement parent, String qName, String defaultNameSpace) {
    super(parent, qName, defaultNameSpace)
  }

  /**
   * Adds text content to this element.
   *
   * @param text the text content
   * @return this element for chaining
   */
  T addContent(String text) {
    element.addText(text)
    this as T
  }

  /**
   * Returns the content value.
   *
   * @return the content value
   */
  String getContent() {
    element.getText()
  }

  /**
   * Replaces the text content of this element.
   *
   * @param content the content
   * @return this element for chaining
   */
  T replaceContent(String content) {
    element.setText(content)
    this as T
  }

  /**
   * Adds CDATA content to this element.
   *
   * @param content the content
   * @return this element for chaining
   */
  T addCdataContent(String content) {
    element.addCDATA(content)
    this as T
  }
}
