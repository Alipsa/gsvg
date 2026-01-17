@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.export.SvgOptimizer
import se.alipsa.groovy.svg.export.SvgRenderer
import examples.shared.ExampleSupport

Svg svg = new Svg(200, 120)
svg.addRect().x(20).y(20).width(160).height(80).fill('lightgray')
svg.addCircle().cx(60).cy(60).r(18).fill('tomato')
svg.addCircle().cx(140).cy(60).r(18).fill('gold')

Svg optimized = SvgOptimizer.optimize(svg, [precision: 1, removeMetadata: true, removeDefaults: true])

File outputFile = ExampleSupport.outputDir().resolve('usecase-optimized.svg').toFile()
SvgRenderer.toSvgFile(optimized, outputFile)
