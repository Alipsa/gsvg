@Grab('se.alipsa.groovy:gsvg:1.0.0')

import se.alipsa.groovy.svg.Circle
import se.alipsa.groovy.svg.Svg
import examples.shared.ExampleSupport

Svg svg = new Svg(260, 120)

Circle base = svg.addCircle().cx(40).cy(60).r(12).fill('tomato')

[[80, 'gold'], [120, 'seagreen'], [160, 'steelblue'], [200, 'plum']].each { cfg ->
  base.cloneWith(svg, [cx: cfg[0], fill: cfg[1]])
}

ExampleSupport.writeSvg(svg, 'clone-variations.svg')
