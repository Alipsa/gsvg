@Grab('se.alipsa.groovy:gsvg:1.0.0')

import se.alipsa.groovy.svg.Svg
import examples.shared.ExampleSupport

Svg svg = new Svg(200, 120)

svg.addG().id('outer').with { g ->
  g.addG().id('inner').addCircle().cx(60).cy(60).r(18).fill('teal')
}

svg.css('g circle').each { it.stroke('black').strokeWidth(2) }

ExampleSupport.writeSvg(svg, 'navigation-descendant-selector.svg')
