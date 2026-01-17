@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.export.SvgRenderer
import examples.shared.ExampleSupport

Svg svg = new Svg(240, 120)

def group = svg.addG()
(0..<4).each { idx ->
  group.addRect()
    .x(20 + (idx * 50))
    .y(40)
    .width(30)
    .height(30)
    .fill('lightgray')
}

svg.css('g > rect:first-child').each { it.fill('tomato') }
svg.css('g > rect:last-child').each { it.fill('seagreen') }
svg.css('g > rect:nth-child(2)').each { it.fill('gold') }

File outputFile = ExampleSupport.outputDir().resolve('navigation-pseudoclasses.svg').toFile()
SvgRenderer.toSvgFile(svg, outputFile)
