@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.export.SvgRenderer
import examples.shared.ExampleSupport

Svg svg = new Svg(200, 120)

def group = svg.addG().id('group1')

def circle = group.addCircle().cx(60).cy(60).r(18).fill('orange')

def parentName = circle.parent?.name

def groupByCss = svg.cssFirst('g#group1')

def same = groupByCss?.is(group)

println "parentName=${parentName} sameGroup=${same}"
File outputFile = ExampleSupport.outputDir().resolve('navigation-parent-traversal.svg').toFile()
SvgRenderer.toSvgFile(svg, outputFile)
