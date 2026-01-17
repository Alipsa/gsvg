@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.io.SvgReader
import se.alipsa.groovy.svg.export.SvgRenderer
import examples.shared.ExampleSupport

import java.io.StringReader

String xml = '''<svg width="180" height="120" xmlns="http://www.w3.org/2000/svg">
  <g id="icons">
    <circle id="a" cx="40" cy="60" r="16" fill="gold" />
    <circle id="b" cx="90" cy="60" r="16" fill="steelblue" />
  </g>
</svg>'''

Svg svg = SvgReader.parse(new StringReader(xml))

svg.xpath('//*[@id="icons"]//circle').each { it.addAttribute('stroke', 'black') }

File outputFile = ExampleSupport.outputDir().resolve('parse-and-query.svg').toFile()
SvgRenderer.toSvgFile(svg, outputFile)
