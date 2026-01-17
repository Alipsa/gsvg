@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import se.alipsa.groovy.svg.G
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.export.SvgRenderer
import examples.shared.ExampleSupport

Svg svg = new Svg(300, 140)

G cluster = svg.addG().id('cluster')
cluster.addCircle().cx(40).cy(50).r(16).fill('tomato')
cluster.addCircle().cx(80).cy(50).r(16).fill('gold')

cluster.cloneWith(svg, [transform: 'translate(140, 20)'])
cluster.cloneWith(svg, [transform: 'translate(140, 80)'])

File outputFile = ExampleSupport.outputDir().resolve('tree-duplicate-group-transform.svg').toFile()
SvgRenderer.toSvgFile(svg, outputFile)
