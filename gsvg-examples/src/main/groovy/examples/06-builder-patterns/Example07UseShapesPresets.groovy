import groovy.transform.Field
import groovy.transform.SourceURI

@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.presets.Shapes
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

Svg svg = new Svg(260, 140)

Shapes.roundedRect(svg, [x: 20, y: 20, width: 80, height: 50, radius: 10]).fill('lightblue')
Shapes.star(svg, [cx: 160, cy: 60, points: 5, outerRadius: 30]).fill('gold')
Shapes.regularPolygon(svg, [cx: 220, cy: 90, sides: 6, radius: 20]).fill('seagreen')

File outputFile = exampleSupport.outputFile('builder-shapes-presets.svg')
SvgRenderer.toSvgFile(svg, outputFile)
