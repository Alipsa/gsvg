@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.export.SvgRenderer
import examples.shared.ExampleSupport

Svg svg = new Svg(200, 120)
svg.addRect().x(20).y(20).width(120).height(60).fill('tomato')

svg.role('img')

def report = svg.validate()
println "valid=${report.isValid()} issues=${report.issues.size()}"

File outputFile = ExampleSupport.outputDir().resolve('advanced-validation-report.svg').toFile()
SvgRenderer.toSvgFile(svg, outputFile)
