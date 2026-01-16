@Grab('se.alipsa.groovy:gsvg:1.0.0')

import se.alipsa.groovy.svg.Svg
import examples.shared.ExampleSupport

Svg svg = new Svg(200, 120)

def rect = svg.addRect().x(40).y(30).width(120).height(60).fill('lightgray')
rect.addClass('card')
rect.toggleClass('active')

println "classes=${rect.getClasses()}"
ExampleSupport.writeSvg(svg, 'attributes-toggle-classes.svg')
