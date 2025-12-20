package se.alipsa.groovy.svg.io


import org.dom4j.Namespace
import org.dom4j.QName
import org.xml.sax.Attributes
import org.xml.sax.InputSource
import org.xml.sax.SAXException
import org.xml.sax.ext.LexicalHandler
import org.xml.sax.helpers.DefaultHandler
import se.alipsa.groovy.svg.A
import se.alipsa.groovy.svg.Animate
import se.alipsa.groovy.svg.AnimateMotion
import se.alipsa.groovy.svg.AnimateTransform
import se.alipsa.groovy.svg.Circle
import se.alipsa.groovy.svg.ClipPath
import se.alipsa.groovy.svg.Defs
import se.alipsa.groovy.svg.Desc
import se.alipsa.groovy.svg.Ellipse
import se.alipsa.groovy.svg.ExternalElementContainer
import se.alipsa.groovy.svg.FeBlend
import se.alipsa.groovy.svg.FeColorMatrix
import se.alipsa.groovy.svg.FeComponentTransfer
import se.alipsa.groovy.svg.FeComposite
import se.alipsa.groovy.svg.FeConvolveMatrix
import se.alipsa.groovy.svg.FeDiffuseLighting
import se.alipsa.groovy.svg.FeDisplacementMap
import se.alipsa.groovy.svg.FeDistantLight
import se.alipsa.groovy.svg.FeDropShadow
import se.alipsa.groovy.svg.FeFlood
import se.alipsa.groovy.svg.FeFuncA
import se.alipsa.groovy.svg.FeFuncB
import se.alipsa.groovy.svg.FeFuncG
import se.alipsa.groovy.svg.FeFuncR
import se.alipsa.groovy.svg.FeGaussianBlur
import se.alipsa.groovy.svg.FeImage
import se.alipsa.groovy.svg.FeMerge
import se.alipsa.groovy.svg.FeMergeNode
import se.alipsa.groovy.svg.FeMorphology
import se.alipsa.groovy.svg.FeOffset
import se.alipsa.groovy.svg.FePointLight
import se.alipsa.groovy.svg.FeSpecularLighting
import se.alipsa.groovy.svg.FeSpotLight
import se.alipsa.groovy.svg.FeTile
import se.alipsa.groovy.svg.FeTurbulence
import se.alipsa.groovy.svg.Filter
import se.alipsa.groovy.svg.ForeignObject
import se.alipsa.groovy.svg.G
import se.alipsa.groovy.svg.Image
import se.alipsa.groovy.svg.Line
import se.alipsa.groovy.svg.LinearGradient
import se.alipsa.groovy.svg.Marker
import se.alipsa.groovy.svg.Mask
import se.alipsa.groovy.svg.Metadata
import se.alipsa.groovy.svg.Mpath
import se.alipsa.groovy.svg.Path
import se.alipsa.groovy.svg.Pattern
import se.alipsa.groovy.svg.Polygon
import se.alipsa.groovy.svg.Polyline
import se.alipsa.groovy.svg.RadialGradient
import se.alipsa.groovy.svg.Rect
import se.alipsa.groovy.svg.Script
import se.alipsa.groovy.svg.Set
import se.alipsa.groovy.svg.Stop
import se.alipsa.groovy.svg.StringContentContainer
import se.alipsa.groovy.svg.Style
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.SvgElement
import se.alipsa.groovy.svg.Switch
import se.alipsa.groovy.svg.Symbol
import se.alipsa.groovy.svg.Text
import se.alipsa.groovy.svg.TextPath
import se.alipsa.groovy.svg.Title
import se.alipsa.groovy.svg.Tspan
import se.alipsa.groovy.svg.Use
import se.alipsa.groovy.svg.View

import javax.xml.XMLConstants
import javax.xml.parsers.SAXParser
import javax.xml.parsers.SAXParserFactory

class SvgReader extends DefaultHandler implements LexicalHandler {

  Svg svg
  SvgElement currentElement
  boolean rootSvgAssigned = false
  boolean isCdataSection = false

  @Override
  void startDocument() throws SAXException {
  }

  @Override
  void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    //print('begin element ' + qName)
    // Note, we do no checking of weather the XML is properly constructed or not e.g.
    // we do not check that a defs element only exists under a svg element, that a marker only exists in a defs etc.
    String elementName = (localName == null || localName.isBlank()) ? qName : localName
    switch (elementName) {
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
      case Image.NAME -> currentElement = currentElement.addImage()
      case Line.NAME -> currentElement = currentElement.addLine()
      case LinearGradient.NAME -> currentElement = currentElement.addLinearGradient()
      case Marker.NAME -> currentElement = currentElement.addMarker()
      case Mask.NAME -> currentElement = currentElement.addMask()
      case Metadata.NAME -> currentElement = currentElement.addMetadata()
      case Mpath.NAME -> currentElement = currentElement.addMpath()
      case Path.NAME -> currentElement = currentElement.addPath()
      case Pattern.NAME -> currentElement = currentElement.addPattern()
      case Polygon.NAME -> currentElement = currentElement.addPolygon()
      case Polyline.NAME -> currentElement = currentElement.addPolyline()
      case RadialGradient.NAME -> currentElement = currentElement.addRadialGradient()
      case Rect.NAME -> currentElement = currentElement.addRect()
      case Script.NAME -> currentElement = currentElement.addScript()
      case Set.NAME -> currentElement = currentElement.addSet()
      case Stop.NAME -> currentElement = currentElement.addStop()
      case Style.NAME -> currentElement = currentElement.addStyle()
      case Svg.NAME -> {
        if (!rootSvgAssigned) {
          currentElement = svg = new Svg()
          rootSvgAssigned = true
        } else {
          currentElement = currentElement.addSvg()
        }
      }
      case Switch.NAME -> currentElement = currentElement.addSwitch()
      case Symbol.NAME -> currentElement = currentElement.addSymbol()
      case Text.NAME -> currentElement = currentElement.addText()
      case TextPath.NAME -> currentElement = currentElement.addTextPath()
      case Title.NAME -> currentElement = currentElement.addTitle()
      case Tspan.NAME -> currentElement = currentElement.addTspan()
      case Use.NAME -> currentElement = currentElement.addUse()
      case View.NAME -> currentElement = currentElement.addView()
      default -> {
        if (currentElement instanceof ExternalElementContainer) {
          if (qName.contains(':')) {
            // a foreignElement or metadata element with a namespace
            String prefix = qName.substring(0, qName.indexOf(':'))
            String local = (localName == null || localName.isBlank()) ? qName.substring(qName.indexOf(':') + 1) : localName
            Namespace ns = new Namespace(prefix, uri)
            QName qn = new QName(local, ns)
            currentElement = currentElement.addElement(qn)
            //println("added qName '$qName', uri='$uri', localName='$localName' to ${currentElement.name}")
          } else if (uri != null && !uri.isBlank()) {
            // a "plain" element or a foreignElement with a default namespace
            String local = (localName == null || localName.isBlank()) ? qName : localName
            Namespace ns = new Namespace('', uri)
            QName qn = new QName(local, ns)
            currentElement = currentElement.addElement(qn)
          } else {
            // a "plain" element or a foreignElement with a default namespace
            currentElement = currentElement.addElement(qName)
          }
        } else {
          throw new SAXException("qName '$qName' is unknown for '${currentElement.name}' to the SvgReader (uri='$uri', localName='$localName') , dont know what to do")
        }
      }
    }
    //println(', current element is now ' + currentElement.element.getName())
    // First capture namespace declarations to ensure prefixes are bound before attribute parsing
    for (int i = 0; i < attributes.getLength(); i++) {
      String attrLocalName = attributes.getLocalName(i)
      if (attrLocalName == null || attrLocalName.isBlank()) {
        String qn = attributes.getQName(i)
        if ('xmlns' == qn) {
          currentElement.addNamespace('', attributes.getValue(i))
        } else if (qn != null && qn.startsWith('xmlns:')) {
          String prefix = qn.substring('xmlns:'.length())
          currentElement.addNamespace(prefix, attributes.getValue(i))
        }
      }
    }

    for (int i = 0; i < attributes.getLength(); i++) {
      String attrLocalName = attributes.getLocalName(i)
      if (attrLocalName != null && !attrLocalName.isBlank()) {
        currentElement.addAttribute(attributes.getQName(i), attributes.getValue(i))
      }
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
    if (currentElement instanceof StringContentContainer) {
      //println('found ' + text + ', current element is ' + currentElement.element.getName())
      if(isCdataSection) {
        currentElement.element.addCDATA(text)
      } else {
        currentElement = currentElement.addContent(text)
      }
    } else {
      if (!text.isBlank()) {
        //println('found ' + text + ', current element is ' + currentElement.name)
        throw new SAXException("character data is not allowed in " + currentElement.name)
      }
    }
  }

  @Override
  void startPrefixMapping(String prefix, String uri) throws SAXException {
    // do nothing, we handle namespace declaration in startElement as this method is called before the element is created
    //if (currentElement.element.getNamespacePrefix() != prefix) {
    //  println("Adding namespace $prefix with uri $uri to ${currentElement.name}")
    //}
  }

  static Svg parse(File svgFile) {
    SvgReader reader = new SvgReader()
    createSAXParser(reader).parse(svgFile, reader)
    reader.svg
  }

  static Svg parse(InputSource svgFile) {
    SvgReader reader = new SvgReader()
    createSAXParser(reader).parse(svgFile, reader)
    reader.svg
  }

  static SAXParser createSAXParser(SvgReader reader) {
    SAXParserFactory factory = SAXParserFactory.newInstance()
    factory.setNamespaceAware(true)
    factory.setXIncludeAware(false)

    // allow us to capture namespace declaration as attributes in startElement
    factory.setFeature('http://xml.org/sax/features/namespace-prefixes', true)
    factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true)
    factory.setFeature("http://xml.org/sax/features/external-general-entities", false)
    factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false)
    factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false)
    factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true)
    SAXParser parser = factory.newSAXParser()
    parser.setProperty("http://xml.org/sax/properties/lexical-handler", reader)
    parser
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

  @Override
  void startDTD(String name, String publicId, String systemId) throws SAXException {

  }

  @Override
  void endDTD() throws SAXException {

  }

  @Override
  void startEntity(String name) throws SAXException {

  }

  @Override
  void endEntity(String name) throws SAXException {

  }

  @Override
  void startCDATA() throws SAXException {
    isCdataSection = true
  }

  @Override
  void endCDATA() throws SAXException {
    isCdataSection = false
  }

  @Override
  void comment(char[] ch, int start, int length) throws SAXException {

  }
}
