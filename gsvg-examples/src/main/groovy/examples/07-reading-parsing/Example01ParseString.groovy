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


String xml = '''<svg width="160" height="100" xmlns="http://www.w3.org/2000/svg">
  <circle id="dot" cx="40" cy="50" r="18" fill="gray" />
  <rect x="80" y="30" width="60" height="40" fill="lightgray" />
</svg>'''

Svg svg = SvgReader.parse(new StringReader(xml))
svg.xpath('//*[@id="dot"]').each { it.addAttribute('fill', 'gold') }

File outputFile = exampleSupport.outputFile('parse-string.svg')
SvgRenderer.toSvgFile(svg, outputFile)
