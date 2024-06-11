package se.alipsa.groovy.svg.io

import org.dom4j.io.OutputFormat
import org.dom4j.io.XMLWriter
import se.alipsa.groovy.svg.Svg

class SvgWriter {

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
