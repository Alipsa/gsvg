import groovy.transform.Field
import groovy.transform.SourceURI

@Grab('se.alipsa.groovy:gsvg:1.1.0')
@Grab('se.alipsa.groovy:gsvg-export:1.1.0')

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

Svg svg = new Svg(400, 180)

// New style: all attributes via fluent rx/ry setters
svg.addEllipse()
  .cx(100)
  .cy(90)
  .rx(80)
  .ry(40)
  .fill('lightsalmon')
  .stroke('orangered')
  .strokeWidth(2)

// Old style: rx/ry via constructor, cx/cy via setters
svg.addEllipse(80, 40)
  .cx(300)
  .cy(90)
  .fill('lightblue')
  .stroke('steelblue')
  .strokeWidth(2)

// Labels
svg.addText('rx/ry fluent setters')
  .x(100)
  .y(155)
  .textAnchor('middle')
  .fontSize(12)
  .fill('#333')

svg.addText('rx/ry via constructor')
  .x(300)
  .y(155)
  .textAnchor('middle')
  .fontSize(12)
  .fill('#333')

File outputFile = exampleSupport.outputFile('builder-ellipse-setters.svg')
SvgRenderer.toSvgFile(svg, outputFile)
