@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import se.alipsa.groovy.svg.Rect
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.export.SvgRenderer
import examples.shared.ExampleSupport

Svg svg = new Svg(220, 120)

Rect rect = svg.addRect().attrs([
  x: 20,
  y: 20,
  width: 120,
  height: 60,
  fill: 'steelblue',
  stroke: 'midnightblue',
  'stroke-width': 2,
  'data-kind': 'primary'
])

println "rx default=${rect.getAttributeOrDefault('rx', '0')}"
println "has data-kind=${rect.hasAttribute('data-kind')}"

rect.removeAttributes { it.startsWith('data-') }

File outputFile = ExampleSupport.outputDir().resolve('attributes-bulk.svg').toFile()
SvgRenderer.toSvgFile(svg, outputFile)
