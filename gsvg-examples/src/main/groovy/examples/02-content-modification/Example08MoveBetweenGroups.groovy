@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import se.alipsa.groovy.svg.G
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.export.SvgRenderer
import examples.shared.ExampleSupport

Svg svg = new Svg(240, 140)

G left = svg.addG().id('left')
G right = svg.addG().id('right').addAttribute('transform', 'translate(120, 0)')

left.addCircle().cx(40).cy(70).r(16).fill('tomato')
def movable = left.addRect().x(70).y(50).width(30).height(40).fill('gold')

movable.clone(right)
left.remove(movable)

File outputFile = ExampleSupport.outputDir().resolve('content-move-between-groups.svg').toFile()
SvgRenderer.toSvgFile(svg, outputFile)
