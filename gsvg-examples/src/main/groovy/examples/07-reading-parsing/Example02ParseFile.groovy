import groovy.transform.Field
import groovy.transform.SourceURI

@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.io.SvgReader
import se.alipsa.groovy.svg.export.SvgRenderer

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
  <rect id="panel" x="20" y="20" width="140" height="80" fill="linen" />
  <circle cx="60" cy="60" r="18" fill="slateblue" />
</svg>'''

File inputFile = exampleSupport.outputFile('parse-input.svg')
inputFile.text = xml

Svg svg = SvgReader.parse(inputFile)
svg.xpath('//*[@id="panel"]').each { it.addAttribute('stroke', 'black') }

File outputFile = exampleSupport.outputFile('parse-file.svg')
SvgRenderer.toSvgFile(svg, outputFile)
