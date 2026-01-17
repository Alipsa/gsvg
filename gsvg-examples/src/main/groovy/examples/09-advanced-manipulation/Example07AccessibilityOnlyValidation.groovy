@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.validation.ValidationEngine
import se.alipsa.groovy.svg.export.SvgRenderer
import examples.shared.ExampleSupport

Svg svg = new Svg(200, 120)
svg.addCircle().cx(60).cy(60).r(18).fill('seagreen')

ValidationEngine engine = ValidationEngine.createAccessibility()

def report = engine.validate(svg)
println "accessibilityIssues=${report.issues.size()}"

File outputFile = ExampleSupport.outputDir().resolve('advanced-accessibility-only.svg').toFile()
SvgRenderer.toSvgFile(svg, outputFile)
