package se.alipsa.groovy.svg.io

import groovy.transform.CompileStatic
import org.dom4j.io.OutputFormat
import org.dom4j.io.XMLWriter
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.SvgIdRewriter

/**
 * Serializes the gsvg object model to SVG XML.
 */
@CompileStatic
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
   * Serializes a namespaced copy of an SVG without changing the caller's model.
   *
   * @param svg SVG to serialize
   * @param prefix prefix applied to ids and their references
   * @return namespaced XML
   */
  static String toXml(Svg svg, String prefix) {
    Svg copy = svg.clone()
    SvgIdRewriter.prefixIds(copy, prefix)
    toXml(copy)
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

  /**
   * Pretty-prints a namespaced copy of an SVG without changing the caller's model.
   *
   * @param svg SVG to serialize
   * @param prefix prefix applied to ids and their references
   * @return namespaced XML
   */
  static String toXmlPretty(Svg svg, String prefix) {
    Svg copy = svg.clone()
    SvgIdRewriter.prefixIds(copy, prefix)
    toXmlPretty(copy)
  }
}
