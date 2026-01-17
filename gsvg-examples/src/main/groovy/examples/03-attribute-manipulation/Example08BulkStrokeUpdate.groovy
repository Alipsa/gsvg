@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.export.SvgRenderer
import examples.shared.ExampleSupport

Svg svg = new Svg(240, 120)

svg.addCircle().cx(40).cy(60).r(14).fill('tomato')
svg.addCircle().cx(100).cy(60).r(14).fill('gold')
svg.addCircle().cx(160).cy(60).r(14).fill('seagreen')

svg.descendants().each { element ->
  element.addAttribute('stroke', 'black')
  element.addAttribute('stroke-width', '2')
}

File outputFile = ExampleSupport.outputDir().resolve('attributes-bulk-stroke.svg').toFile()
SvgRenderer.toSvgFile(svg, outputFile)
