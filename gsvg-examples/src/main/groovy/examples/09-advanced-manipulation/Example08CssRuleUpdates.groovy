@Grab('se.alipsa.groovy:gsvg:1.0.0')

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.css.CssRule
import examples.shared.ExampleSupport

Svg svg = new Svg(200, 120)

CssRule rule = CssRule.of('.badge', [fill: 'gold', stroke: 'black'])
CssRule updated = rule.withDeclaration('opacity', '0.8')

svg.addStyle().addContent(updated.toString())
svg.addCircle().cx(100).cy(60).r(18).addClass('badge')

ExampleSupport.writeSvg(svg, 'advanced-css-rule-updates.svg')
