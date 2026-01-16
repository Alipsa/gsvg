@Grab('se.alipsa.groovy:gsvg:1.0.0')

import se.alipsa.groovy.svg.Circle
import se.alipsa.groovy.svg.Svg
import examples.shared.ExampleSupport

Svg svg = new Svg(240, 120)

svg.addCircle().cx(40).cy(60).r(12).fill('tomato')
svg.addCircle().cx(100).cy(60).r(18).fill('gold')
svg.addCircle().cx(160).cy(60).r(24).fill('seagreen')

List<Integer> radii = svg.findAll(Circle).collect { it.getR() as int }
println "radii=${radii}"

ExampleSupport.writeSvg(svg, 'iteration-map-data.svg')
