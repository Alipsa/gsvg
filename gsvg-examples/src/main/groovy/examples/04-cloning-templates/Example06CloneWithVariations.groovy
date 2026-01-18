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

Svg svg = new Svg(260, 120)

Circle base = svg.addCircle().cx(40).cy(60).r(12).fill('tomato')

[[80, 'gold'], [120, 'seagreen'], [160, 'steelblue'], [200, 'plum']].each { cfg ->
  base.cloneWith(svg, [cx: cfg[0], fill: cfg[1]])
}

File outputFile = exampleSupport.outputFile('clone-variations.svg')
SvgRenderer.toSvgFile(svg, outputFile)
