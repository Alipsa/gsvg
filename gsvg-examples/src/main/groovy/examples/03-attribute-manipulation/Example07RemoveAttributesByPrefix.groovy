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

Svg svg = new Svg(200, 120)

def rect = svg.addRect().x(30).y(30).width(140).height(60).fill('lightblue')
rect.addAttribute('data-id', 'abc')
rect.addAttribute('data-kind', 'sample')
rect.addAttribute('aria-label', 'Panel')

int removed = rect.removeAttributes { it.startsWith('data-') }
println "removed=${removed}"

File outputFile = exampleSupport.outputFile('attributes-remove-prefix.svg')
SvgRenderer.toSvgFile(svg, outputFile)
