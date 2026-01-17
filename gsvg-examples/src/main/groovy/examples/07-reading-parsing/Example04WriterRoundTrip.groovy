@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.io.SvgReader
import se.alipsa.groovy.svg.io.SvgWriter
import se.alipsa.groovy.svg.export.SvgRenderer
import examples.shared.ExampleSupport

import java.io.StringReader

String xml = '''<svg width="120" height="80" xmlns="http://www.w3.org/2000/svg">
  <circle cx="40" cy="40" r="18" fill="plum" />
</svg>'''

Svg svg = SvgReader.parse(new StringReader(xml))
String compact = SvgWriter.toXml(svg)

File outputFile = ExampleSupport.outputDir().resolve('parse-writer-roundtrip.svg').toFile()
outputFile.text = compact
