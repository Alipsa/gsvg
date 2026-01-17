@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.export.SvgRenderer
import examples.shared.ExampleSupport

Svg svg = new Svg(240, 120)

def shapes = [
  [x: 20, y: 20, width: 40, height: 40, fill: 'tomato'],
  [x: 80, y: 20, width: 40, height: 40, fill: 'gold'],
  [x: 140, y: 20, width: 40, height: 40, fill: 'seagreen']
]

shapes.each { cfg ->
  svg.addRect().attrs(cfg)
}

File outputFile = ExampleSupport.outputDir().resolve('builder-with-map.svg').toFile()
SvgRenderer.toSvgFile(svg, outputFile)
