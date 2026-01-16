@Grab('se.alipsa.groovy:gsvg:1.0.0')

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.utils.SvgMerger
import examples.shared.ExampleSupport

Svg top = new Svg(140, 80)
top.addRect().x(20).y(20).width(100).height(40).fill('lavender')

Svg bottom = new Svg(140, 80)
bottom.addCircle().cx(70).cy(40).r(25).fill('lightseagreen')

Svg merged = SvgMerger.mergeVertically(top, bottom)
ExampleSupport.writeSvg(merged, 'tree-merge-vertical.svg')
