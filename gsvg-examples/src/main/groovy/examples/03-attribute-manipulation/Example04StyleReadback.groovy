@Grab('se.alipsa.groovy:gsvg:1.0.0')

import se.alipsa.groovy.svg.Svg
import examples.shared.ExampleSupport

Svg svg = new Svg(200, 120)

def circle = svg.addCircle().cx(100).cy(60).r(25).fill('lightblue')

circle.style([stroke: 'navy', 'stroke-width': '3', opacity: '0.8'])

Map<String, String> styleMap = circle.styleMap
println "stroke=${styleMap['stroke']} opacity=${styleMap['opacity']}"

ExampleSupport.writeSvg(svg, 'attributes-style-readback.svg')
