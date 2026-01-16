@Grab('se.alipsa.groovy:gsvg:1.0.0')

import se.alipsa.groovy.svg.Circle
import se.alipsa.groovy.svg.Svg
import examples.shared.ExampleSupport

Svg svg = new Svg(240, 120)

Circle base = svg.addCircle().cx(50).cy(60).r(18).fill('crimson')
base.cloneWith(svg, [cx: 120, fill: 'royalblue'])
base.cloneWith(svg, [cx: 190, fill: 'seagreen', r: 24])

ExampleSupport.writeSvg(svg, 'clone-with.svg')
