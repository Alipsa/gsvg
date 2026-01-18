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

String template = '''<svg width="240" height="120" xmlns="http://www.w3.org/2000/svg">
  <rect id="panel" x="10" y="10" width="220" height="100" fill="white" stroke="#cccccc" />
  <text id="label" x="120" y="70" text-anchor="middle" font-size="16">Placeholder</text>
</svg>'''

Svg svg = SvgReader.parse(new StringReader(template))

svg.xpath('//*[@id="label"]').each { it.replaceContent('Updated') }
svg.xpath('//*[@id="panel"]').each { it.addAttribute('fill', 'lavender') }

File outputFile = exampleSupport.outputFile('usecase-template-update.svg')
SvgRenderer.toSvgFile(svg, outputFile)
