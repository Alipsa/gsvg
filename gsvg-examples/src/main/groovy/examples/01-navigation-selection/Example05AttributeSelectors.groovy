@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.export.SvgRenderer
import examples.shared.ExampleSupport

Svg svg = new Svg(220, 120)

svg.addCircle().cx(40).cy(60).r(16).fill('tomato')
svg.addCircle().cx(100).cy(60).r(16).fill('gold').stroke('black')
svg.addCircle().cx(160).cy(60).r(16).fill('seagreen').stroke('black')

// CSS attribute selector
svg.css('[stroke]').each { it.strokeWidth(3) }

// XPath attribute selector
svg.xpath('//circle[@fill="tomato"]').each { it.addAttribute('opacity', '0.7') }

File outputFile = ExampleSupport.outputDir().resolve('navigation-attribute-selectors.svg').toFile()
SvgRenderer.toSvgFile(svg, outputFile)
