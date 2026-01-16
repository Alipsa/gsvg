@Grab('se.alipsa.groovy:gsvg:1.0.0')

import se.alipsa.groovy.svg.Svg
import examples.shared.ExampleSupport

Svg svg = new Svg(200, 120)

def group = svg.addG().id('group1')

def circle = group.addCircle().cx(60).cy(60).r(18).fill('orange')

def parentName = circle.parent?.name

def groupByCss = svg.cssFirst('g#group1')

def same = groupByCss?.is(group)

println "parentName=${parentName} sameGroup=${same}"
ExampleSupport.writeSvg(svg, 'navigation-parent-traversal.svg')
