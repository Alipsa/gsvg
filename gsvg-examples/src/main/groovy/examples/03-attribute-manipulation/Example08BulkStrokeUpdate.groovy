@Grab('se.alipsa.groovy:gsvg:1.0.0')

import se.alipsa.groovy.svg.Svg
import examples.shared.ExampleSupport

Svg svg = new Svg(240, 120)

svg.addCircle().cx(40).cy(60).r(14).fill('tomato')
svg.addCircle().cx(100).cy(60).r(14).fill('gold')
svg.addCircle().cx(160).cy(60).r(14).fill('seagreen')

svg.descendants().each { element ->
  element.addAttribute('stroke', 'black')
  element.addAttribute('stroke-width', '2')
}

ExampleSupport.writeSvg(svg, 'attributes-bulk-stroke.svg')
