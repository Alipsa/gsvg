@Grab('se.alipsa.groovy:gsvg:1.0.0')

import se.alipsa.groovy.svg.Circle
import se.alipsa.groovy.svg.Svg
import examples.shared.ExampleSupport

Svg svg = new Svg(220, 120)

svg.addCircle().cx(40).cy(60).r(10).fill('tomato')
svg.addCircle().cx(100).cy(60).r(20).fill('gold')
svg.addCircle().cx(160).cy(60).r(30).fill('seagreen')

svg.findAll(Circle) { (it.getR() as int) >= 20 }.each {
  it.stroke('black').strokeWidth(2)
}

ExampleSupport.writeSvg(svg, 'navigation-findall-predicate.svg')
