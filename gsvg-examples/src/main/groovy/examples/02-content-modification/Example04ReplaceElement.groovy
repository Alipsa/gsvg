@Grab('se.alipsa.groovy:gsvg:1.0.0')

import se.alipsa.groovy.svg.Rect
import se.alipsa.groovy.svg.Svg
import examples.shared.ExampleSupport

Svg svg = new Svg(220, 120)

Rect oldRect = svg.addRect().x(30).y(30).width(80).height(60).fill('lightgray')

// Replace with a circle by cloning attributes manually
svg.remove(oldRect)
svg.addCircle().cx(70).cy(60).r(30).fill('steelblue')

ExampleSupport.writeSvg(svg, 'content-replace-element.svg')
