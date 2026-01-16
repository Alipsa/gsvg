@Grab('se.alipsa.groovy:gsvg:1.0.0')

import se.alipsa.groovy.svg.Svg
import examples.shared.ExampleSupport

Svg svg = new Svg(220, 140)

def outer = svg.addG().id('outer')

def inner = outer.addG().id('inner')
inner.addCircle().cx(70).cy(70).r(18).fill('steelblue')
inner.addRect().x(110).y(50).width(40).height(40).fill('gold')

ExampleSupport.writeSvg(svg, 'content-nested-groups.svg')
