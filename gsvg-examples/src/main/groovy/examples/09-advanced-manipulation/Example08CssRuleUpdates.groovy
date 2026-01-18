import groovy.transform.Field
import groovy.transform.SourceURI

@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.css.CssRule
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

CssRule rule = CssRule.of('.badge', [fill: 'gold', stroke: 'black'])
CssRule updated = rule.withDeclaration('opacity', '0.8')

svg.addStyle().addContent(updated.toString())
svg.addCircle().cx(100).cy(60).r(18).addClass('badge')

File outputFile = exampleSupport.outputFile('advanced-css-rule-updates.svg')
SvgRenderer.toSvgFile(svg, outputFile)
