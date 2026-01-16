@Grab('se.alipsa.groovy:gsvg:1.0.0')

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.io.SvgReader
import examples.shared.ExampleSupport

import java.io.ByteArrayInputStream

String xml = '''<svg width="140" height="100" xmlns="http://www.w3.org/2000/svg">
  <rect x="20" y="20" width="100" height="60" fill="lightblue" />
</svg>'''

InputStream input = new ByteArrayInputStream(xml.bytes)
Svg svg = SvgReader.parse(input)
svg.addCircle().cx(70).cy(50).r(18).fill('tomato')

ExampleSupport.writeSvg(svg, 'parse-inputstream.svg')
