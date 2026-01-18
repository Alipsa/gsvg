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

(0..<4).each { idx ->
  svg.addCircle().cx(40 + (idx * 50)).cy(60).r(14).fill('lightgray')
}

// Move first two circles into a new group
List circles = svg.css('circle')
def group = svg.addG().id('pair')

circles.take(2).each { circle ->
  circle.clone(group)
  svg.remove(circle)
}

group.addAttribute('transform', 'translate(0, 40)')

File outputFile = exampleSupport.outputFile('tree-regroup-selection.svg')
SvgRenderer.toSvgFile(svg, outputFile)
