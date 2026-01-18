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

G outer = svg.addG().id('outer')
G inner = outer.addG().id('inner')
inner.addCircle().cx(60).cy(60).r(16).fill('tomato')
inner.addRect().x(120).y(40).width(40).height(40).fill('gold')

SvgElementFactory.copyChildren(inner, svg)
svg.remove(outer)

File outputFile = exampleSupport.outputFile('tree-flatten-nested-groups.svg')
SvgRenderer.toSvgFile(svg, outputFile)
