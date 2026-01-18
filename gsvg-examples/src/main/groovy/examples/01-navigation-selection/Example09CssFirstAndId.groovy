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

svg.addCircle().id('first').cx(50).cy(60).r(16).fill('tomato')
svg.addCircle().id('second').cx(110).cy(60).r(16).fill('gold')
svg.addCircle().id('third').cx(170).cy(60).r(16).fill('seagreen')

// cssFirst returns a single element
svg.cssFirst('#second')?.addAttribute('stroke', 'black')
svg.cssFirst('circle')?.addAttribute('stroke-width', '3')

File outputFile = exampleSupport.outputFile('navigation-cssfirst-id.svg')
SvgRenderer.toSvgFile(svg, outputFile)
println "wrote ${outputFile.absolutePath}"
