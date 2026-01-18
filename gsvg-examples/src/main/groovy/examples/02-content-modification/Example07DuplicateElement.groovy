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

Svg svg = new Svg(240, 120)

Rect base = svg.addRect().x(30).y(40).width(40).height(40).fill('tomato')
base.cloneWith(svg, [x: 90, fill: 'gold'])
base.cloneWith(svg, [x: 150, fill: 'seagreen'])

File outputFile = exampleSupport.outputFile('content-duplicate-element.svg')
SvgRenderer.toSvgFile(svg, outputFile)
