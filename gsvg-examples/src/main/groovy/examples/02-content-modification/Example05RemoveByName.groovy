@Grab('se.alipsa.groovy:gsvg:1.0.0')

import se.alipsa.groovy.svg.Svg
import examples.shared.ExampleSupport

Svg svg = new Svg(220, 120)

svg.addCircle().cx(50).cy(60).r(16).fill('tomato')
svg.addCircle().cx(100).cy(60).r(16).fill('gold')
svg.addRect().x(150).y(40).width(40).height(40).fill('lightgray')

int removed = svg.removeChild('circle')
println "removed=${removed}"

ExampleSupport.writeSvg(svg, 'content-remove-by-name.svg')
