@Grab('se.alipsa.groovy:gsvg:1.0.0')

import se.alipsa.groovy.svg.Svg
import examples.shared.ExampleSupport

Svg svg = new Svg(320, 140)

svg.createStar(cx: 60, cy: 70, points: 5, outerRadius: 35, innerRadius: 14)
  .fill('gold')
  .stroke('orange')

svg.createArrow(x1: 120, y1: 30, x2: 280, y2: 110, headSize: 12)
  .fill('none')
  .stroke('black')
  .strokeWidth(2)

svg.createSpeechBubble(x: 170, y: 15, width: 120, height: 60, tailX: 240, tailY: 120)
  .fill('aliceblue')
  .stroke('steelblue')

ExampleSupport.writeSvg(svg, 'builder-shape-factories.svg')
