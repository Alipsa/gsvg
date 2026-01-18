import groovy.transform.Field
import groovy.transform.SourceURI

@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.effects.Effects
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

Svg svg = new Svg(240, 140)

def defs = svg.addDefs()
Effects.dropShadow(defs, [id: 'shadow', dx: 3, dy: 3, blur: 4, opacity: 0.4])

svg.addRect()
  .x(30)
  .y(30)
  .width(180)
  .height(80)
  .fill('white')
  .stroke('#888888')
  .filter('url(#shadow)')

svg.addText('Shadow')
  .x(120)
  .y(80)
  .textAnchor('middle')
  .fontSize(16)

File outputFile = exampleSupport.outputFile('advanced-effects-shadow.svg')
SvgRenderer.toSvgFile(svg, outputFile)
