@Grab('se.alipsa.groovy:gsvg:1.0.0')

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.utils.SvgMerger
import examples.shared.ExampleSupport

Svg left = new Svg(120, 120)
left.addCircle().cx(60).cy(60).r(40).fill('gold')

Svg middle = new Svg(120, 120)
middle.addRect().x(20).y(20).width(80).height(80).fill('lightskyblue')

Svg right = new Svg(120, 120)
right.addPolygon('60,10 110,110 10,110').fill('seagreen')

Svg merged = SvgMerger.mergeHorizontally(left, middle, right)
ExampleSupport.writeSvg(merged, 'tree-merge-horizontal.svg')
