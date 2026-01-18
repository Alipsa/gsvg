import groovy.transform.Field
import groovy.transform.SourceURI

@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.utils.PathBuilder
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

PathBuilder triangle = PathBuilder.moveTo(20, 90)
  .lineTo(110, 20)
  .lineTo(200, 90)
  .closePath()

svg.addPath()
  .d(triangle)
  .fill('none')
  .stroke('black')
  .strokeWidth(2)

File outputFile = exampleSupport.outputFile('builder-path.svg')
SvgRenderer.toSvgFile(svg, outputFile)
