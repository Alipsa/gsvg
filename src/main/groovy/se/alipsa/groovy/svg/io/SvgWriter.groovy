package se.alipsa.groovy.svg.io

import org.dom4j.io.OutputFormat
import org.dom4j.io.XMLWriter
import se.alipsa.groovy.svg.Svg

/**
 * Serializes the gsvg object model to SVG XML.
 */
class SvgWriter {

  /**
   * Serializes this element and its children as XML.
   *
   * @param svg the SVG element
   * @return the result
   */
  static String toXml(Svg svg) {
    StringWriter writer = new StringWriter()
    OutputFormat format = new OutputFormat()
    format.setSuppressDeclaration(true)
    XMLWriter xmlWriter = new XMLWriter(writer, format)
    xmlWriter.write(svg.getDocument())
    xmlWriter.close()
    return writer.toString()
  }

  /**
   * To xml pretty.
   *
   * @param svg the SVG element
   * @return the result
   */
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
