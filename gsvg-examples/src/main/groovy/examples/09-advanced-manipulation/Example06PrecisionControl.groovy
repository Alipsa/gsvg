@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.export.SvgRenderer
import examples.shared.ExampleSupport

Svg svg = new Svg(200, 120).setMaxPrecision(1)

svg.addCircle().cx(33.3333).cy(60.6666).r(12.3456).fill('tomato')
svg.addCircle().cx(100.1234).cy(60.5555).r(18.8888).fill('gold')

File outputFile = ExampleSupport.outputDir().resolve('advanced-precision-control.svg').toFile()
SvgRenderer.toSvgFile(svg, outputFile)
