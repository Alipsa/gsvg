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

Svg svg = new Svg(240, 120)

svg.addCircle().cx(40).cy(60).r(16).fill('tomato')
svg.addRect().x(80).y(40).width(40).height(40).fill('gold')
svg.addCircle().cx(160).cy(60).r(16).fill('seagreen')

// Remove all existing elements
svg.children.toList().each { svg.remove(it) }

// Rebuild with new layout
svg.addRect().x(20).y(30).width(80).height(60).fill('lightblue')
svg.addRect().x(120).y(30).width(80).height(60).fill('plum')

File outputFile = exampleSupport.outputFile('content-clear-rebuild.svg')
SvgRenderer.toSvgFile(svg, outputFile)
