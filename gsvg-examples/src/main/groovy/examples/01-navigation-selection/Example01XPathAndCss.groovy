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

def layer = svg.addG().id('layer')
layer.addRect()
  .id('background')
  .x(0)
  .y(0)
  .width(240)
  .height(120)
  .fill('white')
  .stroke('#dddddd')

layer.addCircle('dot').cx(40).cy(60).r(18).fill('red')
layer.addCircle().cx(120).cy(60).r(18).fill('blue').addClass('highlight')
layer.addCircle().cx(200).cy(60).r(18).fill('green').addClass('highlight')

def circlesInLayer = svg.css('g > circle')
def highlights = svg.css('.highlight')
def byId = svg.xpath('//*[@id="dot"]')

println "circlesInLayer=${circlesInLayer.size()} highlights=${highlights.size()} byId=${byId.size()}"
File outputFile = exampleSupport.outputFile('navigation-xpath-css.svg')
SvgRenderer.toSvgFile(svg, outputFile)
