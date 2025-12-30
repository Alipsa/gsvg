package se.alipsa.groovy.svg

import org.dom4j.QName

/**
 * Base class for SVG elements that contain text or CDATA content.
 */
abstract class StringContentContainer<T extends SvgElement<T>> extends SvgElement<T> {


  /**
   * Creates a StringContentContainer.
   *
   * @param parent value
   * @param name value
   */
  StringContentContainer(SvgElement<? extends SvgElement> parent, String name) {
    super(parent, name)
  }

  /**
   * Creates a StringContentContainer.
   *
   * @param parent value
   * @param qName value
   */
  StringContentContainer(SvgElement<? extends SvgElement> parent, QName qName) {
    super(parent, qName)
  }

  /**
   * Creates a StringContentContainer.
   *
   * @param parent value
   * @param qName value
   * @param defaultNameSpace value
   */
  StringContentContainer(SvgElement parent, String qName, String defaultNameSpace) {
    super(parent, qName, defaultNameSpace)
  }

  /**
   * Adds text content to this element.
   *
   * @param text value
   * @return this element for chaining
   */
  T addContent(String text) {
    element.addText(text)
    this
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
   * @param content value
   * @return this element for chaining
   */
  T replaceContent(String content) {
    element.setText(content)
    this
  }

  /**
   * Adds CDATA content to this element.
   *
   * @param content value
   * @return this element for chaining
   */
  T addCdataContent(String content) {
    element.addCDATA(content)
    this
  }
}
