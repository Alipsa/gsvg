@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import se.alipsa.groovy.svg.Circle
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.export.SvgRenderer
import examples.shared.ExampleSupport

Svg svg = new Svg(220, 120)

Circle badge = svg.addCircle().cx(40).cy(40).r(18).fill('gold').stroke('black')
svg.addCircle().cx(100).cy(40).r(14).fill('tomato')
svg.addRect().x(20).y(70).width(80).height(30).fill('lightblue')

svg.remove(badge)
svg.removeChild('circle')

svg.addCircle().cx(160).cy(40).r(18).fill('seagreen')
svg.addRect().x(120).y(70).width(80).height(30).fill('plum')

println "children=${svg.children.size()}"
File outputFile = ExampleSupport.outputDir().resolve('content-add-remove.svg').toFile()
SvgRenderer.toSvgFile(svg, outputFile)
