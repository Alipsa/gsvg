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

Svg svg = new Svg(200, 120)

def rect = svg.addRect().x(40).y(30).width(120).height(60).fill('lightgreen')

def rxValue = rect.getAttributeOrDefault('rx', '0')

def hasStroke = rect.hasAttribute('stroke')

println "rx=${rxValue} hasStroke=${hasStroke}"
File outputFile = exampleSupport.outputFile('attributes-defaults.svg')
SvgRenderer.toSvgFile(svg, outputFile)
