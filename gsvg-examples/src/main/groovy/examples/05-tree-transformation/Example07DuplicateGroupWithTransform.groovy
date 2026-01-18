import groovy.transform.Field
import groovy.transform.SourceURI

@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import se.alipsa.groovy.svg.G
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

Svg svg = new Svg(300, 140)

G cluster = svg.addG().id('cluster')
cluster.addCircle().cx(40).cy(50).r(16).fill('tomato')
cluster.addCircle().cx(80).cy(50).r(16).fill('gold')

cluster.cloneWith(svg, [transform: 'translate(140, 20)'])
cluster.cloneWith(svg, [transform: 'translate(140, 80)'])

File outputFile = exampleSupport.outputFile('tree-duplicate-group-transform.svg')
SvgRenderer.toSvgFile(svg, outputFile)
