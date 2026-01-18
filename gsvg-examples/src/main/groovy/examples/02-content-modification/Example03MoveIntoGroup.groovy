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

Svg svg = new Svg(240, 140)

svg.addRect().x(0).y(0).width(240).height(140).fill('white')

def circle = svg.addCircle().cx(60).cy(70).r(20).fill('coral')

def group = svg.addG().id('moved-group')

def moved = circle.clone(group)
svg.remove(circle)

moved.addAttribute('cx', 180)

File outputFile = exampleSupport.outputFile('content-move-into-group.svg')
SvgRenderer.toSvgFile(svg, outputFile)
