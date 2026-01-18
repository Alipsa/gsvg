import groovy.transform.Field
import groovy.transform.SourceURI

@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import se.alipsa.groovy.svg.Svg
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

Svg svg = new Svg(240, 120)

// Fluent chain
svg.addCircle()
  .cx(50)
  .cy(50)
  .r(20)
  .fill('orange')

// DSL style
svg.addCircle {
  cx 140
  cy 50
  r 20
  fill 'teal'
}

File outputFile = exampleSupport.outputFile('builder-fluent-dsl.svg')
SvgRenderer.toSvgFile(svg, outputFile)
