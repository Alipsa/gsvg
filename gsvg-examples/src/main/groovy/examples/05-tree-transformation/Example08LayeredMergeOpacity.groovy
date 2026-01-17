@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.utils.SvgMerger
import se.alipsa.groovy.svg.export.SvgRenderer
import examples.shared.ExampleSupport

Svg base = new Svg(160, 160)
base.addRect().x(20).y(20).width(120).height(120).fill('steelblue')

Svg overlay = new Svg(160, 160)
overlay.addCircle().cx(80).cy(80).r(50).fill('gold').addAttribute('opacity', '0.6')

def merged = SvgMerger.mergeOnTop(base, overlay)
File outputFile = ExampleSupport.outputDir().resolve('tree-layered-merge-opacity.svg').toFile()
SvgRenderer.toSvgFile(merged, outputFile)
