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

Svg svg = new Svg(240, 120)

Circle base = svg.addCircle().cx(50).cy(60).r(18).fill('crimson')
base.cloneWith(svg, [cx: 120, fill: 'royalblue'])
base.cloneWith(svg, [cx: 190, fill: 'seagreen', r: 24])

File outputFile = exampleSupport.outputFile('clone-with.svg')
SvgRenderer.toSvgFile(svg, outputFile)
