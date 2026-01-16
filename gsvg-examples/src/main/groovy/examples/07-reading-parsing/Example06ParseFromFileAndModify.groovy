@Grab('se.alipsa.groovy:gsvg:1.0.0')

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.io.SvgReader
import examples.shared.ExampleSupport

String xml = '''<svg width="160" height="120" xmlns="http://www.w3.org/2000/svg">
  <rect id="panel" x="20" y="20" width="120" height="80" fill="linen" />
</svg>'''

File file = ExampleSupport.writeText(xml, 'parse-source.svg').toFile()
Svg svg = SvgReader.parse(file)

svg.xpath('//*[@id="panel"]').each { it.addAttribute('stroke', 'black') }
svg.addText('Panel').x(80).y(90).textAnchor('middle').fontSize(12)

ExampleSupport.writeSvg(svg, 'parse-file-modified.svg')
