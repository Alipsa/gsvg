@Grab('se.alipsa.groovy:gsvg:1.0.0')

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.io.SvgReader
import examples.shared.ExampleSupport

String xml = '''<svg width="180" height="120" xmlns="http://www.w3.org/2000/svg">
  <rect id="panel" x="20" y="20" width="140" height="80" fill="linen" />
  <circle cx="60" cy="60" r="18" fill="slateblue" />
</svg>'''

def inputPath = ExampleSupport.writeText(xml, 'parse-input.svg')

Svg svg = SvgReader.parse(inputPath.toFile())
svg.xpath('//*[@id="panel"]').each { it.addAttribute('stroke', 'black') }

ExampleSupport.writeSvg(svg, 'parse-file.svg')
