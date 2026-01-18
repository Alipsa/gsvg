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

Svg svg = new Svg(220, 120)

def rect = svg.addRect().x(30).y(20).width(160).height(80).fill('lavender')
rect.style([
  stroke: 'rebeccapurple',
  'stroke-width': '2',
  opacity: '0.9'
])

rect.addClass('card')
rect.toggleClass('active')
rect.toggleClass('active')

println "stroke=${rect.getStyleProperty('stroke')} classes=${rect.getClasses()}"
File outputFile = exampleSupport.outputFile('attributes-style-classes.svg')
SvgRenderer.toSvgFile(svg, outputFile)
