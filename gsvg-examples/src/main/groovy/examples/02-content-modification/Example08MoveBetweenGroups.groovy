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

Svg svg = new Svg(240, 140)

G left = svg.addG().id('left')
G right = svg.addG().id('right').addAttribute('transform', 'translate(120, 0)')

left.addCircle().cx(40).cy(70).r(16).fill('tomato')
def movable = left.addRect().x(70).y(50).width(30).height(40).fill('gold')

movable.clone(right)
left.remove(movable)

File outputFile = exampleSupport.outputFile('content-move-between-groups.svg')
SvgRenderer.toSvgFile(svg, outputFile)
