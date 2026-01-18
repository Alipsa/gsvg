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

Svg svg = new Svg(300, 140)

def group = svg.addG().id('cluster')

group.addCircle().cx(40).cy(40).r(14).fill('tomato')
group.addCircle().cx(80).cy(40).r(14).fill('gold')

group.cloneWith(svg, [transform: 'translate(140, 40)'])

File outputFile = exampleSupport.outputFile('clone-group.svg')
SvgRenderer.toSvgFile(svg, outputFile)
