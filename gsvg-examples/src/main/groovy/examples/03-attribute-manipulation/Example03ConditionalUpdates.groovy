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

svg.addRect().x(20).y(20).width(60).height(60).fill('tomato')
svg.addRect().x(90).y(20).width(60).height(60).fill('gold')
svg.addRect().x(160).y(20).width(40).height(60).fill('tomato')

svg.descendants().each { element ->
  if (element.getAttribute('fill') == 'tomato') {
    element.addAttribute('opacity', '0.7')
  }
}

File outputFile = exampleSupport.outputFile('attributes-conditional.svg')
SvgRenderer.toSvgFile(svg, outputFile)
