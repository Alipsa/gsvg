import groovy.transform.Field
import groovy.transform.SourceURI

@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import se.alipsa.groovy.svg.Svg
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

Svg svg = new Svg(220, 120)

svg.role('img')
svg.ariaLabel('Status badge')

svg.addCircle().cx(60).cy(60).r(20).fill('seagreen')
svg.addText('OK').x(120).y(65).fontSize(16)

File outputFile = exampleSupport.outputFile('advanced-accessibility.svg')
SvgRenderer.toSvgFile(svg, outputFile)
