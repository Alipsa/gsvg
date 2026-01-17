@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import se.alipsa.groovy.svg.G
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.SvgElementFactory
import se.alipsa.groovy.svg.export.SvgRenderer
import examples.shared.ExampleSupport

Svg svg = new Svg(240, 120)

G group = svg.addG().id('to-flatten')

group.addRect().x(20).y(20).width(60).height(60).fill('gold')
group.addCircle().cx(140).cy(50).r(20).fill('steelblue')

SvgElementFactory.copyChildren(group, svg)
svg.remove(group)

File outputFile = ExampleSupport.outputDir().resolve('tree-flatten-group.svg').toFile()
SvgRenderer.toSvgFile(svg, outputFile)
