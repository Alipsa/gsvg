@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.export.SvgRenderer
import examples.shared.ExampleSupport

Svg svg = new Svg(260, 120)

svg.addText {
  addContent 'Hello gsvg'
  x 130
  y 70
  textAnchor 'middle'
  fontSize 18
}

File outputFile = ExampleSupport.outputDir().resolve('builder-text-dsl.svg').toFile()
SvgRenderer.toSvgFile(svg, outputFile)
