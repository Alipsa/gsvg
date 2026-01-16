@Grab('se.alipsa.groovy:gsvg:1.0.0')

import se.alipsa.groovy.svg.Circle
import se.alipsa.groovy.svg.Svg
import examples.shared.ExampleSupport

Svg svg = new Svg(200, 140)

svg.addCircle().cx(30).cy(40).r(10).fill('orange')
svg.addCircle().cx(90).cy(40).r(25).fill('teal')
svg.addRect().x(20).y(80).width(60).height(40).fill('lightgray')

List<Circle> circles = svg.findAll(Circle)
List<Circle> largeCircles = svg.findAll(Circle) { (it.getR() as int) > 15 }

def descendants = svg.descendants()

def firstOrange = svg.findFirst { it.getAttribute('fill') == 'orange' }

println "circles=${circles.size()} largeCircles=${largeCircles.size()} descendants=${descendants.size()} firstOrange=${firstOrange?.name}"
ExampleSupport.writeSvg(svg, 'navigation-descendants-filter.svg')
