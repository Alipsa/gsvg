package se.alipsa.groovy.svg

import org.dom4j.io.OutputFormat
import org.dom4j.io.XMLWriter

class XmlWriter {

  static String toXml(Svg svg) {
    StringWriter writer = new StringWriter()
    OutputFormat format = new OutputFormat()
    format.setSuppressDeclaration(true)
    XMLWriter xmlWriter = new XMLWriter(writer, format)
    xmlWriter.write(svg.getDocument())
    xmlWriter.close()
    return writer.toString()
  }

  static String toXmlPretty(Svg svg) {
    StringWriter writer = new StringWriter()
    OutputFormat format = OutputFormat.createPrettyPrint()
    format.setSuppressDeclaration(true)
    XMLWriter xmlWriter = new XMLWriter(writer, format)
    xmlWriter.write(svg.getDocument())
    xmlWriter.close()
    return writer.toString()
  }
}
