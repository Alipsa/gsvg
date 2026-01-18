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

// Define a symbol
svg.addDefs().addSymbol([id: 'badge']).with { sym ->
  sym.viewBox(0, 0, 40, 40)
  sym.addCircle().cx(20).cy(20).r(18).fill('tomato')
}

svg.addUse().href('#badge').x(20).y(40)
svg.addUse().href('#badge').x(100).y(40).addAttribute('opacity', '0.7')
svg.addUse().href('#badge').x(180).y(40).addAttribute('opacity', '0.4')

File outputFile = exampleSupport.outputFile('advanced-use-symbol.svg')
SvgRenderer.toSvgFile(svg, outputFile)
