@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.utils.PathBuilder
import se.alipsa.groovy.svg.export.SvgRenderer
import examples.shared.ExampleSupport

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

File outputFile = ExampleSupport.outputDir().resolve('builder-path.svg').toFile()
SvgRenderer.toSvgFile(svg, outputFile)
