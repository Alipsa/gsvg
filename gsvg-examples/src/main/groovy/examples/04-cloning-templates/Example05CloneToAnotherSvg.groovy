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

Svg source = new Svg(120, 120)
source.addCircle().cx(60).cy(60).r(30).fill('plum')

Svg target = new Svg(240, 120)
source.descendants().each { it.clone(target) }

File outputFile = exampleSupport.outputFile('clone-to-another-svg.svg')
SvgRenderer.toSvgFile(target, outputFile)
