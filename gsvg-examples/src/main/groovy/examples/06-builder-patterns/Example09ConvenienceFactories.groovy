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

Svg svg = new Svg(400, 200)

// Circle with center and radius
svg.addCircle(50, 50, 30)
  .fill('coral')
  .stroke('darkred')
  .strokeWidth(2)

// Ellipse with center and radii
svg.addEllipse(160, 50, 60, 30)
  .fill('skyblue')
  .stroke('steelblue')
  .strokeWidth(2)

// Rectangle with position and size
svg.addRect(250, 20, 100, 60)
  .fill('palegreen')
  .stroke('seagreen')
  .strokeWidth(2)

// Path from data string
svg.addPathData('M10,130 L50,100 L90,140 L130,110 Z')
  .fill('gold')
  .stroke('goldenrod')
  .strokeWidth(2)

// Polyline from points string
svg.addPolyline('160,140 200,100 240,130 280,100 320,140')
  .fill('none')
  .stroke('purple')
  .strokeWidth(3)

// No-arg polygon factory with points set via fluent setter
svg.addPolygon()
  .points('340,100 360,140 380,100 400,140')
  .fill('tomato')
  .stroke('firebrick')
  .strokeWidth(2)

File outputFile = exampleSupport.outputFile('builder-convenience-factories.svg')
SvgRenderer.toSvgFile(svg, outputFile)
