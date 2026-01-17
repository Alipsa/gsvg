@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.export.SvgRenderer
import examples.shared.ExampleSupport

Svg svg = new Svg(420, 240)
svg.addRect().x(0).y(0).width(420).height(240).fill('white')

def data = [
  [label: 'Q1', value: 18, color: 'tomato'],
  [label: 'Q2', value: 28, color: 'goldenrod'],
  [label: 'Q3', value: 22, color: 'seagreen'],
  [label: 'Q4', value: 30, color: 'steelblue']
]

int maxValue = data.collect { it.value as int }.max() as int
int chartHeight = 140
int baseY = 190
int barWidth = 60
int gap = 20
int startX = 40

data.eachWithIndex { item, idx ->
  int barHeight = (chartHeight * (item.value as int) / maxValue) as int
  int x = startX + (idx * (barWidth + gap))
  int y = baseY - barHeight

  svg.addRect()
    .x(x)
    .y(y)
    .width(barWidth)
    .height(barHeight)
    .fill(item.color as String)

  svg.addText(item.label as String)
    .x(x + (barWidth / 2))
    .y(baseY + 20)
    .textAnchor('middle')
    .fontSize(12)
}

File outputFile = ExampleSupport.outputDir().resolve('usecase-bar-chart.svg').toFile()
SvgRenderer.toSvgFile(svg, outputFile)
