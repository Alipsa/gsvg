@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.export.SvgRenderer
import examples.shared.ExampleSupport

Svg svg = new Svg(240, 120)

def source = svg.addCircle().cx(60).cy(60).r(18).fill('salmon')

def targetGroup = svg.addG().id('moved')
targetGroup.addRect().x(130).y(20).width(80).height(80).fill('none').stroke('#999999')

def moved = source.cloneWith(targetGroup, [cx: 170, fill: 'slateblue'])
svg.remove(source)

println "moved=${moved?.name}"
File outputFile = ExampleSupport.outputDir().resolve('content-clone-reparent.svg').toFile()
SvgRenderer.toSvgFile(svg, outputFile)
