package se.alipsa.groovy.svg

import org.dom4j.QName

abstract class StringContentContainer<T extends SvgElement<T>> extends SvgElement<T> {


  StringContentContainer(SvgElement<? extends SvgElement> parent, String name) {
    super(parent, name)
  }

  StringContentContainer(SvgElement<? extends SvgElement> parent, QName qName) {
    super(parent, qName)
  }

  StringContentContainer(SvgElement parent, String qName, String defaultNameSpace) {
    super(parent, qName, defaultNameSpace)
  }

  T addContent(String text) {
    element.addText(text)
    this
  }

  String getContent() {
    element.getText()
  }

  T replaceContent(String content) {
    element.setText(content)
    this
  }

  T addCdataContent(String content) {
    element.addCDATA(content)
    this
  }
}