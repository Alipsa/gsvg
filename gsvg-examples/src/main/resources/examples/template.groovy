// Title:
// Purpose:
// Tags:
// Output:

@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import se.alipsa.groovy.svg.Svg
import examples.shared.ExampleSupport

Svg svg = new Svg(200, 200)

svg.addCircle()
   .cx(100)
   .cy(100)
   .r(60)
   .fill('gold')

println svg.toXmlPretty()

// ExampleSupport.writeSvg(svg, 'example.svg')
