import groovy.transform.Field
import groovy.transform.SourceURI

@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import se.alipsa.groovy.svg.Circle
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

svg.addCircle().cx(40).cy(60).r(12).fill('tomato')
svg.addCircle().cx(100).cy(60).r(12).fill('gold')
svg.addCircle().cx(160).cy(60).r(12).fill('seagreen')

List<String> centers = svg.findAll(Circle).collect { "${it.getCx()},${it.getCy()}" }
println "centers=${centers}"

File outputFile = exampleSupport.outputFile('iteration-collect-centers.svg')
SvgRenderer.toSvgFile(svg, outputFile)
