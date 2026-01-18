import groovy.transform.Field
import groovy.transform.SourceURI

@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import se.alipsa.groovy.svg.Rect
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

Rect oldRect = svg.addRect().x(30).y(30).width(80).height(60).fill('lightgray')

// Replace with a circle by cloning attributes manually
svg.remove(oldRect)
svg.addCircle().cx(70).cy(60).r(30).fill('steelblue')

File outputFile = exampleSupport.outputFile('content-replace-element.svg')
SvgRenderer.toSvgFile(svg, outputFile)
