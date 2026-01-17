@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.export.SvgRenderer
import examples.shared.ExampleSupport

Svg svg = new Svg(240, 120)

// Fluent chain
svg.addCircle()
  .cx(50)
  .cy(50)
  .r(20)
  .fill('orange')

// DSL style
svg.addCircle {
  cx 140
  cy 50
  r 20
  fill 'teal'
}

File outputFile = ExampleSupport.outputDir().resolve('builder-fluent-dsl.svg').toFile()
SvgRenderer.toSvgFile(svg, outputFile)
