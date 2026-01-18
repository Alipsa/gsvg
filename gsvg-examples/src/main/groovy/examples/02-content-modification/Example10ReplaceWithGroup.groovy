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

Svg svg = new Svg(220, 120)

// Replace a single circle with a grouped icon

def circle = svg.addCircle().cx(80).cy(60).r(20).fill('lightgray')

svg.remove(circle)

def group = svg.addG().id('icon')
group.addCircle().cx(80).cy(60).r(18).fill('tomato')
group.addLine().x1(65).y1(60).x2(95).y2(60).stroke('white').strokeWidth(4)

group.addLine().x1(80).y1(45).x2(80).y2(75).stroke('white').strokeWidth(4)

File outputFile = exampleSupport.outputFile('content-replace-with-group.svg')
SvgRenderer.toSvgFile(svg, outputFile)
