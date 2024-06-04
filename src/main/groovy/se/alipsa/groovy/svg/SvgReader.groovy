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
      case AnimateMotion.NAME -> currentElement = currentElement.addAnimateMotion()
      case AnimateTransform.NAME -> currentElement = currentElement.addAnimateTransform()
      case Circle.NAME -> currentElement = currentElement.addCircle()
      case ClipPath.NAME -> currentElement = currentElement.addClipPath()
      case Defs.NAME -> currentElement = currentElement.addDefs()
      case Desc.NAME -> currentElement = currentElement.addDesc()
      case Ellipse.NAME -> currentElement = currentElement.addEllipse()
      case FeBlend.NAME -> currentElement = currentElement.addFeBlend()
      case FeColorMatrix.NAME -> currentElement = currentElement.addFeColorMatrix()
      case FeComponentTransfer.NAME -> currentElement = currentElement.addFeComponentTransfer()
      case FeComposite.NAME -> currentElement = currentElement.addFeComposite()
      case FeConvolveMatrix.NAME -> currentElement = currentElement.addFeConvolveMatrix()
      case FeDiffuseLighting.NAME -> currentElement = currentElement.addFeDiffuseLighting()
      case FeDisplacementMap.NAME -> currentElement = currentElement.addFeDisplacementMap()
      case FeDistantLight.NAME -> currentElement = currentElement.addFeDistantLight()
      case FeDropShadow.NAME -> currentElement = currentElement.addFeDropShadow()
      case FeFlood.NAME -> currentElement = currentElement.addFeFlood()
      case FeFuncA.NAME -> currentElement = currentElement.addFeFuncA()
      case FeFuncB.NAME -> currentElement = currentElement.addFeFuncB()
      case FeFuncG.NAME -> currentElement = currentElement.addFeFuncG()
      case FeFuncR.NAME -> currentElement = currentElement.addFeFuncR()
      case FeGaussianBlur.NAME -> currentElement = currentElement.addFeGaussianBlur()
      case FeImage.NAME -> currentElement = currentElement.addFeImage()
      case FeMerge.NAME -> currentElement = currentElement.addFeMerge()
      case FeMergeNode.NAME -> currentElement = currentElement.addFeMergeNode()
      case FeMorphology.NAME -> currentElement = currentElement.addFeMorphology()
      case FeOffset.NAME -> currentElement = currentElement.addFeOffset()
      case FePointLight.NAME -> currentElement = currentElement.addFePointLight()
      case FeSpecularLighting.NAME -> currentElement = currentElement.addFeSpecularLighting()
      case FeSpotLight.NAME -> currentElement = currentElement.addFeSpotLight()
      case FeTile.NAME -> currentElement = currentElement.addFeTile()
      case FeTurbulence.NAME -> currentElement = currentElement.addFeTurbulence()
      case Filter.NAME -> currentElement = currentElement.addFilter()
      case ForeignObject.NAME -> currentElement = currentElement.addForeignObject()
      case G.NAME -> currentElement = currentElement.addG()
      case Line.NAME -> currentElement = currentElement.addLine()
      case LinearGradient.NAME -> currentElement = currentElement.addLinearGradient()
      case Marker.NAME -> currentElement = currentElement.addMarker()
      case Path.NAME -> currentElement = currentElement.addPath()
      case Pattern.NAME -> currentElement = currentElement.addPattern()
      case Polygon.NAME -> currentElement = currentElement.addPolygon()
      case Polyline.NAME -> currentElement = currentElement.addPolyline()
      case RadialGradient.NAME -> currentElement = currentElement.addRadialGradient()
      case Rect.NAME -> currentElement = currentElement.addRect()
      case Stop.NAME -> currentElement = currentElement.addStop()
      case Style.NAME -> currentElement = currentElement.addStyle()
      case Svg.NAME -> currentElement = svg
      case Text.NAME -> currentElement = currentElement.addText()
      case Title.NAME -> currentElement = currentElement.addTitle()
      case Tspan.NAME -> currentElement = currentElement.addTspan()
      case Use.NAME -> currentElement = currentElement.addUse()
      default -> {
        if (currentElement.class in [ForeignObject, ForeignElement]) {
          currentElement = currentElement.addElement(qName)
        } else {
          throw new SAXException("$qName is unknown to the SvgReader, dont know what to do")
        }
      }
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
    if (currentElement.class in [Text, Tspan, Title, Desc, Style, ForeignObject, ForeignElement]) {
      //println('found ' + text + ', current element is ' + currentElement.element.getName())
      currentElement = currentElement.addContent(text)
    } else {
      if (!text.isBlank()) {
        println('found ' + text + ', current element is ' + currentElement.element.getName())
        throw new SAXException("character data is not allowed in " + currentElement.element.getName())
      }
    }
  }

  static Svg parse(File svgFile) {
    SvgReader reader = new SvgReader()
    createSAXParser().parse(svgFile, reader)
    reader.svg
  }

  static Svg parse(InputSource svgFile) {
    SvgReader reader = new SvgReader()
    createSAXParser().parse(svgFile, reader)
    reader.svg
  }

  static SAXParser createSAXParser() {
    SAXParserFactory factory = SAXParserFactory.newInstance()
    //factory.setNamespaceAware(true)
    factory.newSAXParser()
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
