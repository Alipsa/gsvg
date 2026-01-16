@Grab('se.alipsa.groovy:gsvg:1.0.0')

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.io.SvgReader
import examples.shared.ExampleSupport

import java.io.StringReader

String xml = '''<svg width="160" height="100" xmlns="http://www.w3.org/2000/svg">
  <circle id="dot" cx="40" cy="50" r="18" fill="gray" />
  <rect x="80" y="30" width="60" height="40" fill="lightgray" />
</svg>'''

Svg svg = SvgReader.parse(new StringReader(xml))
svg.xpath('//*[@id="dot"]').each { it.addAttribute('fill', 'gold') }

ExampleSupport.writeSvg(svg, 'parse-string.svg')
