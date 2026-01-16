@Grab('se.alipsa.groovy:gsvg:1.0.0')

import se.alipsa.groovy.svg.Svg
import examples.shared.ExampleSupport

Svg svg = new Svg(240, 120)

def points = [20, 80, 140, 200]
points.eachWithIndex { x, idx ->
  svg.addCircle().cx(x).cy(60).r(12 + (idx * 2)).fill('slateblue')
}

ExampleSupport.writeSvg(svg, 'builder-from-list.svg')
