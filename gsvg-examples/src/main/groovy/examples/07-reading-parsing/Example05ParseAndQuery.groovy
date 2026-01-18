import groovy.transform.Field
import groovy.transform.SourceURI

@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.io.SvgReader
import se.alipsa.groovy.svg.export.SvgRenderer
import java.io.StringReader

@SourceURI
@Field
URI scriptUri

File scriptDir = new File(scriptUri).parentFile
File helper = new File(scriptDir.parentFile.parentFile, 'helper.groovy')

if (!helper.exists()) {
  throw new IllegalStateException("Cannot find helper script at ${helper.absolutePath}")
}
def exampleSupport = evaluate(helper)
exampleSupport.scriptDir = scriptDir


String xml = '''<svg width="180" height="120" xmlns="http://www.w3.org/2000/svg">
  <g id="icons">
    <circle id="a" cx="40" cy="60" r="16" fill="gold" />
    <circle id="b" cx="90" cy="60" r="16" fill="steelblue" />
  </g>
</svg>'''

Svg svg = SvgReader.parse(new StringReader(xml))

svg.xpath('//*[@id="icons"]//circle').each { it.addAttribute('stroke', 'black') }

File outputFile = exampleSupport.outputFile('parse-and-query.svg')
SvgRenderer.toSvgFile(svg, outputFile)
