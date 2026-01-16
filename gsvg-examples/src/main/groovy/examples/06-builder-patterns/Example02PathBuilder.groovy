@Grab('se.alipsa.groovy:gsvg:1.0.0')

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.utils.PathBuilder
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

ExampleSupport.writeSvg(svg, 'builder-path.svg')
