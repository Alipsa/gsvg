import groovy.transform.Field
import groovy.transform.SourceURI

@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import se.alipsa.groovy.svg.G
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.SvgElementFactory
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

G group = svg.addG().id('to-flatten')

group.addRect().x(20).y(20).width(60).height(60).fill('gold')
group.addCircle().cx(140).cy(50).r(20).fill('steelblue')

SvgElementFactory.copyChildren(group, svg)
svg.remove(group)

File outputFile = exampleSupport.outputFile('tree-flatten-group.svg')
SvgRenderer.toSvgFile(svg, outputFile)
