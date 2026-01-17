@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.io.SvgReader
import se.alipsa.groovy.svg.export.SvgRenderer
import examples.shared.ExampleSupport

String xml = '''<svg width="180" height="120" xmlns="http://www.w3.org/2000/svg">
  <rect id="panel" x="20" y="20" width="140" height="80" fill="linen" />
  <circle cx="60" cy="60" r="18" fill="slateblue" />
</svg>'''

File inputFile = ExampleSupport.outputDir().resolve('parse-input.svg').toFile()
inputFile.text = xml

Svg svg = SvgReader.parse(inputFile)
svg.xpath('//*[@id="panel"]').each { it.addAttribute('stroke', 'black') }

File outputFile = ExampleSupport.outputDir().resolve('parse-file.svg').toFile()
SvgRenderer.toSvgFile(svg, outputFile)
