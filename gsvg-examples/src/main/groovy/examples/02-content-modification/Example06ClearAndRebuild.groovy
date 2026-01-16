@Grab('se.alipsa.groovy:gsvg:1.0.0')

import se.alipsa.groovy.svg.Svg
import examples.shared.ExampleSupport

Svg svg = new Svg(240, 120)

svg.addCircle().cx(40).cy(60).r(16).fill('tomato')
svg.addRect().x(80).y(40).width(40).height(40).fill('gold')
svg.addCircle().cx(160).cy(60).r(16).fill('seagreen')

// Remove all existing elements
svg.children.toList().each { svg.remove(it) }

// Rebuild with new layout
svg.addRect().x(20).y(30).width(80).height(60).fill('lightblue')
svg.addRect().x(120).y(30).width(80).height(60).fill('plum')

ExampleSupport.writeSvg(svg, 'content-clear-rebuild.svg')
