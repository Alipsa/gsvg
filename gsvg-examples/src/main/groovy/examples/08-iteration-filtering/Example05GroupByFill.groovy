@Grab('se.alipsa.groovy:gsvg:1.0.0')

import se.alipsa.groovy.svg.Svg
import examples.shared.ExampleSupport

Svg svg = new Svg(240, 120)

svg.addRect().x(20).y(20).width(40).height(40).fill('tomato')
svg.addRect().x(80).y(20).width(40).height(40).fill('gold')
svg.addRect().x(140).y(20).width(40).height(40).fill('tomato')

Map grouped = svg.descendants().groupBy { it.getAttribute('fill') }
println "groupedKeys=${grouped.keySet()}"

ExampleSupport.writeSvg(svg, 'iteration-group-by-fill.svg')
