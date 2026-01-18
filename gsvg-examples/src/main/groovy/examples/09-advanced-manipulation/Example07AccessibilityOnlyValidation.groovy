import groovy.transform.Field
import groovy.transform.SourceURI

@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.validation.ValidationEngine
import se.alipsa.groovy.svg.export.SvgRenderer

@SourceURI
@Field
URI scriptUri

File scriptDir = new File(scriptUri).parentFile
File helper = new File(scriptDir.parentFile.parentFile, 'helper.groovy')

if (!helper.exists()) {
  throw new IllegalStateException("Cannot find helper script at ${helper.absolutePath}")
}
def exampleSupport = evaluate(helper)
exampleSupport.scriptDir = scriptDir

Svg svg = new Svg(200, 120)
svg.addCircle().cx(60).cy(60).r(18).fill('seagreen')

ValidationEngine engine = ValidationEngine.createAccessibility()

def report = engine.validate(svg)
println "accessibilityIssues=${report.issues.size()}"

File outputFile = exampleSupport.outputFile('advanced-accessibility-only.svg')
SvgRenderer.toSvgFile(svg, outputFile)
