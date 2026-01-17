@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.export.SvgRenderer
import examples.shared.ExampleSupport

Svg svg = new Svg(220, 120)

svg.role('img')
svg.ariaLabel('Status badge')

svg.addCircle().cx(60).cy(60).r(20).fill('seagreen')
svg.addText('OK').x(120).y(65).fontSize(16)

File outputFile = ExampleSupport.outputDir().resolve('advanced-accessibility.svg').toFile()
SvgRenderer.toSvgFile(svg, outputFile)
