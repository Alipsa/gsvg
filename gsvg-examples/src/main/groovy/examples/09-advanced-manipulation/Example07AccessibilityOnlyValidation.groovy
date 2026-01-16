@Grab('se.alipsa.groovy:gsvg:1.0.0')

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.validation.ValidationEngine
import examples.shared.ExampleSupport

Svg svg = new Svg(200, 120)
svg.addCircle().cx(60).cy(60).r(18).fill('seagreen')

ValidationEngine engine = ValidationEngine.createAccessibility()

def report = engine.validate(svg)
println "accessibilityIssues=${report.issues.size()}"

ExampleSupport.writeSvg(svg, 'advanced-accessibility-only.svg')
