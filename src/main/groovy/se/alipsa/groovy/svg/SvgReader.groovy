package se.alipsa.groovy.svg

import org.xml.sax.Attributes
import org.xml.sax.InputSource
import org.xml.sax.SAXException
import org.xml.sax.helpers.DefaultHandler

import javax.xml.parsers.SAXParser
import javax.xml.parsers.SAXParserFactory

class SvgReader extends DefaultHandler {

  Svg svg
  SvgElement currentElement

  @Override
  void startDocument() throws SAXException {
    currentElement = new Svg()
    svg = currentElement
  }

  @Override
  void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    //print('begin element ' + qName)
    switch (qName) {
      case Circle.NAME -> currentElement = currentElement.addCircle()
      case Ellipse.NAME -> currentElement = currentElement.addEllipse()
      case G.NAME -> currentElement = currentElement.addG()
      case Line.NAME -> currentElement = currentElement.addLine()
      case Path.NAME -> currentElement = currentElement.addPath()
      case Polygon.NAME -> currentElement = currentElement.addPolygon()
      case Polyline.NAME -> currentElement = currentElement.addPolyline()
      case Rect.NAME -> currentElement = currentElement.addRect()
      case Text.NAME -> currentElement = currentElement.addText()
      case Tspan.NAME -> currentElement = currentElement.addTspan()
    }
    //println(', current element is now ' + currentElement.element.getName())
    for (int i = 0; i < attributes.getLength(); i++) {
      currentElement.addAttribute(attributes.getLocalName(i), attributes.getValue(i))
    }
  }

  @Override
  void endElement(String uri, String localName, String qName) throws SAXException {
    //print('end element ' + qName)
    currentElement = currentElement.getParent()
    //println(', current element is now ' + currentElement.element.getName())
  }

  @Override
  void characters(char[] ch, int start, int length) throws SAXException {
    String text = new String(ch, start, length)
    //println('found ' + text + ', current element is ' + currentElement.element.getName() )
    currentElement = currentElement.addContent(text)
  }

  static Svg parse(File svgFile) {
    SAXParserFactory factory = SAXParserFactory.newInstance();
    SAXParser saxParser = factory.newSAXParser();
    SvgReader reader = new SvgReader()
    saxParser.parse(svgFile, reader);
  }

  static Svg parse(InputSource svgFile) {
    SAXParserFactory factory = SAXParserFactory.newInstance()
    SAXParser saxParser = factory.newSAXParser()
    SvgReader reader = new SvgReader()
    saxParser.parse(svgFile, reader)
    reader.svg
  }

  static Svg parse(InputStream svgFile) {
    parse(new InputSource(svgFile))
  }

  static Svg parse(Reader reader) {
    parse(new InputSource(reader))
  }

  static Svg parse(String content) {
    try (StringReader reader = new StringReader(content)) {
      return parse(reader)
    }
  }
}
