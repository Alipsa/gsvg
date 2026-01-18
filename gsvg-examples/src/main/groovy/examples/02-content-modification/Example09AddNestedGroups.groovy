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

Svg svg = new Svg(220, 140)

def outer = svg.addG().id('outer')

def inner = outer.addG().id('inner')
inner.addCircle().cx(70).cy(70).r(18).fill('steelblue')
inner.addRect().x(110).y(50).width(40).height(40).fill('gold')

File outputFile = exampleSupport.outputFile('content-nested-groups.svg')
SvgRenderer.toSvgFile(svg, outputFile)
