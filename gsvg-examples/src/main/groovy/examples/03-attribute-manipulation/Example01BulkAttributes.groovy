import groovy.transform.Field
import groovy.transform.SourceURI

@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import se.alipsa.groovy.svg.Rect
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

Svg svg = new Svg(220, 120)

Rect rect = svg.addRect().attrs([
  x: 20,
  y: 20,
  width: 120,
  height: 60,
  fill: 'steelblue',
  stroke: 'midnightblue',
  'stroke-width': 2,
  'data-kind': 'primary'
])

println "rx default=${rect.getAttributeOrDefault('rx', '0')}"
println "has data-kind=${rect.hasAttribute('data-kind')}"

rect.removeAttributes { it.startsWith('data-') }

File outputFile = exampleSupport.outputFile('attributes-bulk.svg')
SvgRenderer.toSvgFile(svg, outputFile)
