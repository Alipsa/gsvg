@Grab('se.alipsa.groovy:gsvg:1.0.0')

import se.alipsa.groovy.svg.G
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.SvgElementFactory
import examples.shared.ExampleSupport

Svg svg = new Svg(240, 120)

G outer = svg.addG().id('outer')
G inner = outer.addG().id('inner')
inner.addCircle().cx(60).cy(60).r(16).fill('tomato')
inner.addRect().x(120).y(40).width(40).height(40).fill('gold')

SvgElementFactory.copyChildren(inner, svg)
svg.remove(outer)

ExampleSupport.writeSvg(svg, 'tree-flatten-nested-groups.svg')
