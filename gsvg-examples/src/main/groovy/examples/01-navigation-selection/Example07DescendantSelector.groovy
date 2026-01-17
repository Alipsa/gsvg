@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.export.SvgRenderer
import examples.shared.ExampleSupport

Svg svg = new Svg(200, 120)

svg.addG().id('outer').with { g ->
  g.addG().id('inner').addCircle().cx(60).cy(60).r(18).fill('teal')
}

svg.css('g circle').each { it.stroke('black').strokeWidth(2) }

File outputFile = ExampleSupport.outputDir().resolve('navigation-descendant-selector.svg').toFile()
SvgRenderer.toSvgFile(svg, outputFile)
