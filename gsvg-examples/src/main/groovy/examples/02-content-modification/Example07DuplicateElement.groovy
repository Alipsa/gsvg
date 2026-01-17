@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import se.alipsa.groovy.svg.Rect
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.export.SvgRenderer
import examples.shared.ExampleSupport

Svg svg = new Svg(240, 120)

Rect base = svg.addRect().x(30).y(40).width(40).height(40).fill('tomato')
base.cloneWith(svg, [x: 90, fill: 'gold'])
base.cloneWith(svg, [x: 150, fill: 'seagreen'])

File outputFile = ExampleSupport.outputDir().resolve('content-duplicate-element.svg').toFile()
SvgRenderer.toSvgFile(svg, outputFile)
