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

Svg svg = new Svg(400, 260)

// Define markers in defs
def defs = svg.addDefs()

// Arrow marker for line ends
defs.addMarker('arrowEnd')
  .markerWidth(10)
  .markerHeight(7)
  .refX(10)
  .refY(3.5)
  .orient('auto')
  .addPath().d('M0,0 L10,3.5 L0,7 Z').fill('steelblue')

// Circle marker for start points
defs.addMarker('circleStart')
  .markerWidth(8)
  .markerHeight(8)
  .refX(4)
  .refY(4)
  .addCircle(4, 4, 3).fill('coral')

// Diamond marker for midpoints
defs.addMarker('diamondMid')
  .markerWidth(10)
  .markerHeight(10)
  .refX(5)
  .refY(5)
  .addPath().d('M5,0 L10,5 L5,10 L0,5 Z').fill('seagreen')

// Line with start and end markers
svg.addLine(30, 40, 370, 40)
  .stroke('steelblue')
  .strokeWidth(2)
  .markerStart('url(#circleStart)')
  .markerEnd('url(#arrowEnd)')

svg.addText('Line with markerStart and markerEnd')
  .x(200)
  .y(65)
  .textAnchor('middle')
  .fontSize(11)
  .fill('#555')

// Path with all three markers
svg.addPathData('M30,120 L130,90 L250,130 L370,100')
  .fill('none')
  .stroke('coral')
  .strokeWidth(2)
  .markerStart('url(#circleStart)')
  .markerMid('url(#diamondMid)')
  .markerEnd('url(#arrowEnd)')

svg.addText('Path with markerStart, markerMid, and markerEnd')
  .x(200)
  .y(155)
  .textAnchor('middle')
  .fontSize(11)
  .fill('#555')

// Polyline with all three markers
svg.addPolyline('30,210 100,180 200,220 300,190 370,210')
  .fill('none')
  .stroke('seagreen')
  .strokeWidth(2)
  .markerStart('url(#circleStart)')
  .markerMid('url(#diamondMid)')
  .markerEnd('url(#arrowEnd)')

svg.addText('Polyline with markerStart, markerMid, and markerEnd')
  .x(200)
  .y(245)
  .textAnchor('middle')
  .fontSize(11)
  .fill('#555')

File outputFile = exampleSupport.outputFile('advanced-marker-support.svg')
SvgRenderer.toSvgFile(svg, outputFile)
