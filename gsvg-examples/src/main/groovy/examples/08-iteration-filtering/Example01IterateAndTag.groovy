@Grab('se.alipsa.groovy:gsvg:1.0.0')

import se.alipsa.groovy.svg.Svg
import examples.shared.ExampleSupport

Svg svg = new Svg(260, 120)

svg.addCircle().cx(40).cy(60).r(16).fill('tomato')
svg.addCircle().cx(100).cy(60).r(16).fill('gold')
svg.addCircle().cx(160).cy(60).r(16).fill('seagreen')
svg.addRect().x(200).y(40).width(40).height(40).fill('lightgray')

svg.descendants().eachWithIndex { element, idx ->
  element.addAttribute('data-index', idx)
}

ExampleSupport.writeSvg(svg, 'iteration-tagging.svg')
