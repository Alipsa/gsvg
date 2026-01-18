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

Svg top = new Svg(140, 80)
top.addRect().x(20).y(20).width(100).height(40).fill('lavender')

Svg bottom = new Svg(140, 80)
bottom.addCircle().cx(70).cy(40).r(25).fill('lightseagreen')

Svg merged = SvgMerger.mergeVertically(top, bottom)
File outputFile = exampleSupport.outputFile('tree-merge-vertical.svg')
SvgRenderer.toSvgFile(merged, outputFile)
