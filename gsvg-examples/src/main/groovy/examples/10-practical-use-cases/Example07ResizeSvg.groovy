import groovy.transform.Field
import groovy.transform.SourceURI
@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.export.SvgRenderer
import se.alipsa.groovy.svg.export.SvgResizer

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

Svg svg = new Svg(400, 200)
svg.addRect().x(10).y(10).width(380).height(180).fill('linen').stroke('sienna')
svg.addText('Resize me').x(20).y(120).fontSize(36).fill('sienna')

Svg resized = SvgResizer.resizeToWidth(svg, 480)
Svg percentScaled = SvgResizer.resizeToWidth(svg, '150%')

File outputFixed = exampleSupport.outputFile('usecase-resize-svg-480.svg')
SvgRenderer.toSvgFile(resized, outputFixed)

File outputPercent = exampleSupport.outputFile('usecase-resize-svg-150pct.svg')
SvgRenderer.toSvgFile(percentScaled, outputPercent)

println "resized=${outputFixed.name}"
println "percent=${outputPercent.name}"
