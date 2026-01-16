@Grab('se.alipsa.groovy:gsvg:1.0.0')

import se.alipsa.groovy.svg.Circle
import se.alipsa.groovy.svg.Svg
import examples.shared.ExampleSupport

Svg svg = new Svg(200, 120)

svg.addCircle().cx(40).cy(60).r(12).fill('tomato')
svg.addCircle().cx(100).cy(60).r(12).fill('gold')
svg.addCircle().cx(160).cy(60).r(12).fill('seagreen')

List<String> centers = svg.findAll(Circle).collect { "${it.getCx()},${it.getCy()}" }
println "centers=${centers}"

ExampleSupport.writeSvg(svg, 'iteration-collect-centers.svg')
