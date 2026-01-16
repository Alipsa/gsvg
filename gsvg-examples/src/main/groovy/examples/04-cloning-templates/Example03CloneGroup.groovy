@Grab('se.alipsa.groovy:gsvg:1.0.0')

import se.alipsa.groovy.svg.Svg
import examples.shared.ExampleSupport

Svg svg = new Svg(300, 140)

def group = svg.addG().id('cluster')

group.addCircle().cx(40).cy(40).r(14).fill('tomato')
group.addCircle().cx(80).cy(40).r(14).fill('gold')

group.cloneWith(svg, [transform: 'translate(140, 40)'])

ExampleSupport.writeSvg(svg, 'clone-group.svg')
