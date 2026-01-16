@Grab('se.alipsa.groovy:gsvg:1.0.0')

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.css.CssRule
import se.alipsa.groovy.svg.css.CssStyleSheet
import examples.shared.ExampleSupport

Svg svg = new Svg(220, 120)

CssStyleSheet sheet = new CssStyleSheet()
CssRule rule = new CssRule('.highlight', [fill: 'gold', stroke: 'black'])

sheet = sheet.withRule(rule)

svg.addStyle().addContent(sheet.toCss())

svg.addCircle().cx(60).cy(60).r(16).addClass('highlight')
svg.addRect().x(120).y(40).width(60).height(40).addClass('highlight')

ExampleSupport.writeSvg(svg, 'advanced-css-stylesheet.svg')
