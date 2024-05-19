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
    svg = new Svg()
  }

  @Override
  void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    //print('begin element ' + qName)
    // Note, we do no checking of weather the XML is properly constructed or not e.g.
    // we do not check that a defs element only exists under a svg element, that a marker only exists in a defs etc.
    switch (qName) {
      case A.NAME -> currentElement = currentElement.addA()
      case Animate.NAME -> currentElement = currentElement.addAnimate()
      case Circle.NAME -> currentElement = currentElement.addCircle()
      case ClipPath.NAME -> currentElement = currentElement.addClipPath()
      case Defs.NAME -> currentElement = currentElement.addDefs()
      case Ellipse.NAME -> currentElement = currentElement.addEllipse()
      case G.NAME -> currentElement = currentElement.addG()
      case Line.NAME -> currentElement = currentElement.addLine()
      case LinearGradient.NAME -> currentElement = currentElement.addLinearGradient()
      case Marker.NAME -> currentElement = currentElement.addMarker()
      case Path.NAME -> currentElement = currentElement.addPath()
      case Polygon.NAME -> currentElement = currentElement.addPolygon()
      case Polyline.NAME -> currentElement = currentElement.addPolyline()
      case RadialGradient.NAME -> currentElement = currentElement.addRadialGradient()
      case Rect.NAME -> currentElement = currentElement.addRect()
      case Stop.NAME -> currentElement = currentElement.addStop()
      case Svg.NAME -> currentElement = svg
      case Text.NAME -> currentElement = currentElement.addText()
      case Tspan.NAME -> currentElement = currentElement.addTspan()
      case Use.NAME -> currentElement = currentElement.addUse()
      default -> throw new SAXException("$qName is unknown to the SvgReader, dont know what to do")
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
    if (currentElement.element.getName() in ['text', 'tspan']) {
      //println('found ' + text + ', current element is ' + currentElement.element.getName())
      currentElement = currentElement.addContent(text)
    }
  }

  static Svg parse(File svgFile) {
    SAXParserFactory factory = SAXParserFactory.newInstance();
    SAXParser saxParser = factory.newSAXParser();
    SvgReader reader = new SvgReader()
    saxParser.parse(svgFile, reader)
    reader.svg
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
