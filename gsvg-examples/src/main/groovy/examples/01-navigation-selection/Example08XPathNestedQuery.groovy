@Grab('se.alipsa.groovy:gsvg:1.0.0')

import se.alipsa.groovy.svg.Svg
import examples.shared.ExampleSupport

Svg svg = new Svg(220, 120)

def g1 = svg.addG().id('g1')

g1.addRect().x(20).y(20).width(60).height(60).fill('lightblue')

g1.addG().id('g2').addCircle().cx(140).cy(60).r(18).fill('plum')

svg.xpath('//g[@id="g1"]//circle').each { it.addAttribute('stroke', 'black') }

ExampleSupport.writeSvg(svg, 'navigation-xpath-nested.svg')
