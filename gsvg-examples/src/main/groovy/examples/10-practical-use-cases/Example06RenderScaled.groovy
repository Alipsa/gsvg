@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.export.SvgRenderer
import examples.shared.ExampleSupport

Svg svg = new Svg(200, 120)
svg.addCircle().cx(60).cy(60).r(20).fill('tomato')
svg.addCircle().cx(140).cy(60).r(20).fill('gold')

File output = ExampleSupport.outputDir().resolve('usecase-render-scaled.png').toFile()
SvgRenderer.toPng(svg, output, [scale: 3.0, antialiasing: true])

println "rendered=${output.name}"
