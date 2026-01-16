@Grab('se.alipsa.groovy:gsvg:1.0.0')

import se.alipsa.groovy.svg.Svg
import examples.shared.ExampleSupport

Svg svg = new Svg(220, 120)

svg.addRect().x(20).y(20).width(60).height(60).fill('tomato')
svg.addRect().x(90).y(20).width(60).height(60).fill('gold')
svg.addRect().x(160).y(20).width(40).height(60).fill('tomato')

svg.descendants().each { element ->
  if (element.getAttribute('fill') == 'tomato') {
    element.addAttribute('opacity', '0.7')
  }
}

ExampleSupport.writeSvg(svg, 'attributes-conditional.svg')
