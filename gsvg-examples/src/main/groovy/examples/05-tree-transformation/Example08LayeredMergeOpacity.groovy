import groovy.transform.Field
import groovy.transform.SourceURI

@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.utils.SvgMerger
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

Svg base = new Svg(160, 160)
base.addRect().x(20).y(20).width(120).height(120).fill('steelblue')

Svg overlay = new Svg(160, 160)
overlay.addCircle().cx(80).cy(80).r(50).fill('gold').addAttribute('opacity', '0.6')

def merged = SvgMerger.mergeOnTop(base, overlay)
File outputFile = exampleSupport.outputFile('tree-layered-merge-opacity.svg')
SvgRenderer.toSvgFile(merged, outputFile)
