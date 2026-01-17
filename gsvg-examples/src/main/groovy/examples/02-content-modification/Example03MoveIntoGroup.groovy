@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.export.SvgRenderer
import examples.shared.ExampleSupport

Svg svg = new Svg(240, 140)

svg.addRect().x(0).y(0).width(240).height(140).fill('white')

def circle = svg.addCircle().cx(60).cy(70).r(20).fill('coral')

def group = svg.addG().id('moved-group')

def moved = circle.clone(group)
svg.remove(circle)

moved.addAttribute('cx', 180)

File outputFile = ExampleSupport.outputDir().resolve('content-move-into-group.svg').toFile()
SvgRenderer.toSvgFile(svg, outputFile)
