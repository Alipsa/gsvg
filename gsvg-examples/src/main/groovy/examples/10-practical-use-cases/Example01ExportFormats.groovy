@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import groovy.transform.Field
import groovy.transform.SourceURI
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.export.SvgFormatter
import se.alipsa.groovy.svg.export.SvgOptimizer
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

Svg svg = new Svg(300, 120)
svg.addRect().x(0).y(0).width(300).height(120).fill('white')
svg.addCircle().cx(60).cy(60).r(30).fill('coral')
svg.addCircle().cx(150).cy(60).r(30).fill('steelblue')
svg.addCircle().cx(240).cy(60).r(30).fill('seagreen')

String pretty = SvgFormatter.prettify(svg, [indent: '  '])
File prettyFile = exampleSupport.outputFile('export-pretty.svg')
prettyFile.text = pretty

Svg optimized = SvgOptimizer.optimize(svg, [removeComments: true, precision: 2])
File optimizedFile = exampleSupport.outputFile('export-optimized.svg')
SvgRenderer.toSvgFile(optimized, optimizedFile)

File pngFile = exampleSupport.outputFile('export.png')
SvgRenderer.toPng(svg, pngFile, [width: 600, height: 240, antialiasing: true])

File jpegFile = exampleSupport.outputFile('export.jpg')
SvgRenderer.toJpeg(svg, jpegFile, [width: 600, height: 240, backgroundColor: 'white', quality: 0.9])

println "wrote ${pngFile} and ${jpegFile}"
