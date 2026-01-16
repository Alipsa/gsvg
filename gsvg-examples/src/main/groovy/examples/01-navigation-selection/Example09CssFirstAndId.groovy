@Grab('se.alipsa.groovy:gsvg:1.0.0')

import se.alipsa.groovy.svg.Svg
import examples.shared.ExampleSupport

Svg svg = new Svg(220, 120)

svg.addCircle().id('first').cx(50).cy(60).r(16).fill('tomato')
svg.addCircle().id('second').cx(110).cy(60).r(16).fill('gold')
svg.addCircle().id('third').cx(170).cy(60).r(16).fill('seagreen')

// cssFirst returns a single element
svg.cssFirst('#second')?.addAttribute('stroke', 'black')
svg.cssFirst('circle')?.addAttribute('stroke-width', '3')

ExampleSupport.writeSvg(svg, 'navigation-cssfirst-id.svg')
