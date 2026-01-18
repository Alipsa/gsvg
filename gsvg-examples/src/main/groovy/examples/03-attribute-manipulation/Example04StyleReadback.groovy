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

Svg svg = new Svg(200, 120)

def circle = svg.addCircle().cx(100).cy(60).r(25).fill('lightblue')

circle.style([stroke: 'navy', 'stroke-width': '3', opacity: '0.8'])

Map<String, String> styleMap = circle.styleMap
println "stroke=${styleMap['stroke']} opacity=${styleMap['opacity']}"

File outputFile = exampleSupport.outputFile('attributes-style-readback.svg')
SvgRenderer.toSvgFile(svg, outputFile)
