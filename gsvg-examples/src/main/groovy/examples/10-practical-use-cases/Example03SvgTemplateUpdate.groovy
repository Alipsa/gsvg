@Grab('se.alipsa.groovy:gsvg:1.0.0')

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.io.SvgReader
import examples.shared.ExampleSupport

import java.io.StringReader

String template = '''<svg width="240" height="120" xmlns="http://www.w3.org/2000/svg">
  <rect id="panel" x="10" y="10" width="220" height="100" fill="white" stroke="#cccccc" />
  <text id="label" x="120" y="70" text-anchor="middle" font-size="16">Placeholder</text>
</svg>'''

Svg svg = SvgReader.parse(new StringReader(template))

svg.xpath('//*[@id="label"]').each { it.text('Updated') }
svg.xpath('//*[@id="panel"]').each { it.addAttribute('fill', 'lavender') }

ExampleSupport.writeSvg(svg, 'usecase-template-update.svg')
