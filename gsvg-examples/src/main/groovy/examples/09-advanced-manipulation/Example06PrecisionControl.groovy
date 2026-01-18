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

Svg svg = new Svg(200, 120).setMaxPrecision(1)

svg.addCircle().cx(33.3333).cy(60.6666).r(12.3456).fill('tomato')
svg.addCircle().cx(100.1234).cy(60.5555).r(18.8888).fill('gold')

File outputFile = exampleSupport.outputFile('advanced-precision-control.svg')
SvgRenderer.toSvgFile(svg, outputFile)
