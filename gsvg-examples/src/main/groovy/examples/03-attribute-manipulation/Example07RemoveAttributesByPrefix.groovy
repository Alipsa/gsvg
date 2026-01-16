@Grab('se.alipsa.groovy:gsvg:1.0.0')

import se.alipsa.groovy.svg.Svg
import examples.shared.ExampleSupport

Svg svg = new Svg(200, 120)

def rect = svg.addRect().x(30).y(30).width(140).height(60).fill('lightblue')
rect.addAttribute('data-id', 'abc')
rect.addAttribute('data-kind', 'sample')
rect.addAttribute('aria-label', 'Panel')

int removed = rect.removeAttributes { it.startsWith('data-') }
println "removed=${removed}"

ExampleSupport.writeSvg(svg, 'attributes-remove-prefix.svg')
