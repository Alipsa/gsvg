@Grab('se.alipsa.groovy:gsvg:1.0.0')

import se.alipsa.groovy.svg.Circle
import se.alipsa.groovy.svg.Svg
import examples.shared.ExampleSupport

Svg svg = new Svg(200, 120)

svg.addRect().x(10).y(10).width(180).height(100).fill('white').stroke('#cccccc')
svg.addCircle().cx(50).cy(60).r(14).fill('tomato')
svg.addCircle().cx(100).cy(60).r(14).fill('gold')
svg.addCircle().cx(150).cy(60).r(14).fill('teal')

def circlesByName = svg['circle']
def circlesByClass = svg[Circle]
def firstCircle = svg.findFirst(Circle)
def secondElement = svg[1]

println "circlesByName=${circlesByName.size()} circlesByClass=${circlesByClass.size()} first=${firstCircle?.name} second=${secondElement?.name}"
ExampleSupport.writeSvg(svg, 'navigation-getat-findfirst.svg')
