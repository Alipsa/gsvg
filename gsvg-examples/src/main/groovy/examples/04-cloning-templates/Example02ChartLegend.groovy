@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.templates.ChartLegend
import se.alipsa.groovy.svg.export.SvgRenderer
import examples.shared.ExampleSupport

Svg svg = new Svg(360, 200)
svg.addRect().x(0).y(0).width(360).height(200).fill('white')

def items = [
  [color: 'tomato', label: 'Apples'],
  [color: 'gold', label: 'Bananas'],
  [color: 'seagreen', label: 'Cherries']
]

ChartLegend legend = new ChartLegend()
legend.apply(svg, [x: 220, y: 30, items: items])

items.eachWithIndex { item, idx ->
  svg.addRect()
    .x(40)
    .y(50 + (idx * 35))
    .width(120)
    .height(20)
    .fill(item.color as String)
}

File outputFile = ExampleSupport.outputDir().resolve('clone-template-legend.svg').toFile()
SvgRenderer.toSvgFile(svg, outputFile)
