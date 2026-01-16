@Grab('se.alipsa.groovy:gsvg:1.0.0')

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.presets.Shapes
import examples.shared.ExampleSupport

Svg svg = new Svg(260, 140)

Shapes.roundedRect(svg, [x: 20, y: 20, width: 80, height: 50, radius: 10]).fill('lightblue')
Shapes.star(svg, [cx: 160, cy: 60, points: 5, outerRadius: 30]).fill('gold')
Shapes.regularPolygon(svg, [cx: 220, cy: 90, sides: 6, radius: 20]).fill('seagreen')

ExampleSupport.writeSvg(svg, 'builder-shapes-presets.svg')
