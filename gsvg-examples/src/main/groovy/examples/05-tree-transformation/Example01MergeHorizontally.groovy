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

Svg left = new Svg(120, 120)
left.addCircle().cx(60).cy(60).r(40).fill('gold')

Svg middle = new Svg(120, 120)
middle.addRect().x(20).y(20).width(80).height(80).fill('lightskyblue')

Svg right = new Svg(120, 120)
right.addPolygon('60,10 110,110 10,110').fill('seagreen')

Svg merged = SvgMerger.mergeHorizontally(left, middle, right)
File outputFile = exampleSupport.outputFile('tree-merge-horizontal.svg')
SvgRenderer.toSvgFile(merged, outputFile)
