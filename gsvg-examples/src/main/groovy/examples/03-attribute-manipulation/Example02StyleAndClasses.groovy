@Grab('se.alipsa.groovy:gsvg:1.0.0')

import se.alipsa.groovy.svg.Svg
import examples.shared.ExampleSupport

Svg svg = new Svg(220, 120)

def rect = svg.addRect().x(30).y(20).width(160).height(80).fill('lavender')
rect.style([
  stroke: 'rebeccapurple',
  'stroke-width': '2',
  opacity: '0.9'
])

rect.addClass('card')
rect.toggleClass('active')
rect.toggleClass('active')

println "stroke=${rect.getStyleProperty('stroke')} classes=${rect.getClasses()}"
ExampleSupport.writeSvg(svg, 'attributes-style-classes.svg')
