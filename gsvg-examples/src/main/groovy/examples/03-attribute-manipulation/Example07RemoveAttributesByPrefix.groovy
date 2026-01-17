@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.export.SvgRenderer
import examples.shared.ExampleSupport

Svg svg = new Svg(200, 120)

def rect = svg.addRect().x(30).y(30).width(140).height(60).fill('lightblue')
rect.addAttribute('data-id', 'abc')
rect.addAttribute('data-kind', 'sample')
rect.addAttribute('aria-label', 'Panel')

int removed = rect.removeAttributes { it.startsWith('data-') }
println "removed=${removed}"

File outputFile = ExampleSupport.outputDir().resolve('attributes-remove-prefix.svg').toFile()
SvgRenderer.toSvgFile(svg, outputFile)
