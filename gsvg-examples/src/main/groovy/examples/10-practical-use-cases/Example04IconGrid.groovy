@Grab('se.alipsa.groovy:gsvg:1.0.0')

import se.alipsa.groovy.svg.Svg
import examples.shared.ExampleSupport

Svg svg = new Svg(260, 200)

int startX = 30
int startY = 30
int gap = 40

(0..<3).each { row ->
  (0..<4).each { col ->
    int x = startX + (col * gap)
    int y = startY + (row * gap)
    svg.addCircle().cx(x).cy(y).r(12).fill('steelblue')
  }
}

ExampleSupport.writeSvg(svg, 'usecase-icon-grid.svg')
