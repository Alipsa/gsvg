import groovy.transform.Field
import groovy.transform.SourceURI

@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.css.CssRule
import se.alipsa.groovy.svg.css.CssStyleSheet
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

CssStyleSheet sheet = CssStyleSheet.empty()
CssRule rule = CssRule.of('.highlight', [fill: 'gold', stroke: 'black'])

sheet.addRule(rule)

svg.addStyle().addContent(sheet.toString())

svg.addCircle().cx(60).cy(60).r(16).addClass('highlight')
svg.addRect().x(120).y(40).width(60).height(40).addClass('highlight')

File outputFile = exampleSupport.outputFile('advanced-css-stylesheet.svg')
SvgRenderer.toSvgFile(svg, outputFile)
