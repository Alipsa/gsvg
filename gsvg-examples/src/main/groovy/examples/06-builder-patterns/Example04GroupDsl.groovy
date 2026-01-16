@Grab('se.alipsa.groovy:gsvg:1.0.0')

import se.alipsa.groovy.svg.Svg
import examples.shared.ExampleSupport

Svg svg = new Svg(240, 140)

svg.addG {
  id 'dsl-group'
  addRect {
    x 20
    y 20
    width 80
    height 60
    fill 'lightblue'
  }
  addCircle {
    cx 160
    cy 50
    r 20
    fill 'tomato'
  }
}

ExampleSupport.writeSvg(svg, 'builder-group-dsl.svg')
