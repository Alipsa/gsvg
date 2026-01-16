@Grab('se.alipsa.groovy:gsvg:1.0.0')

import se.alipsa.groovy.svg.Svg
import examples.shared.ExampleSupport

Svg svg = new Svg(200, 120)

def rect = svg.addRect().x(40).y(30).width(120).height(60).fill('lightgreen')

def rxValue = rect.getAttributeOrDefault('rx', '0')

def hasStroke = rect.hasAttribute('stroke')

println "rx=${rxValue} hasStroke=${hasStroke}"
ExampleSupport.writeSvg(svg, 'attributes-defaults.svg')
