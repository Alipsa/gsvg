@Grab('se.alipsa.groovy:gsvg:1.0.0')

import se.alipsa.groovy.svg.Svg
import examples.shared.ExampleSupport

Svg svg = new Svg(240, 140)

(0..<4).each { idx ->
  svg.addCircle().cx(40 + (idx * 50)).cy(60).r(14).fill('lightgray')
}

// Move first two circles into a new group
List circles = svg.css('circle')
def group = svg.addG().id('pair')

circles.take(2).each { circle ->
  circle.clone(group)
  svg.remove(circle)
}

group.addAttribute('transform', 'translate(0, 40)')

ExampleSupport.writeSvg(svg, 'tree-regroup-selection.svg')
