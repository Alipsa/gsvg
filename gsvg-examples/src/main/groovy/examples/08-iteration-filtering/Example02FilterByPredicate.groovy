@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.export.SvgRenderer
import examples.shared.ExampleSupport

Svg svg = new Svg(220, 120)

svg.addRect().x(20).y(20).width(60).height(60).fill('tomato')
svg.addRect().x(90).y(20).width(60).height(60).fill('steelblue')
svg.addRect().x(160).y(20).width(40).height(60).fill('tomato')

svg.filter { it.getAttribute('fill') == 'tomato' }.each {
  it.stroke('black').strokeWidth(2)
}

File outputFile = ExampleSupport.outputDir().resolve('iteration-filter-by-fill.svg').toFile()
SvgRenderer.toSvgFile(svg, outputFile)
