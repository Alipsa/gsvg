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

def g1 = svg.addG().id('g1')

g1.addRect().x(20).y(20).width(60).height(60).fill('lightblue')

g1.addG().id('g2').addCircle().cx(140).cy(60).r(18).fill('plum')

svg.xpath('//g[@id="g1"]//circle').each { it.addAttribute('stroke', 'black') }

File outputFile = exampleSupport.outputFile('navigation-xpath-nested.svg')
SvgRenderer.toSvgFile(svg, outputFile)
println "wrote ${outputFile.absolutePath}"
