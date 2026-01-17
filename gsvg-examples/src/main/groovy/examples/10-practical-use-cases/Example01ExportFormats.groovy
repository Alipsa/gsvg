@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.export.SvgFormatter
import se.alipsa.groovy.svg.export.SvgOptimizer
import se.alipsa.groovy.svg.export.SvgRenderer
import examples.shared.ExampleSupport

Svg svg = new Svg(300, 120)
svg.addRect().x(0).y(0).width(300).height(120).fill('white')
svg.addCircle().cx(60).cy(60).r(30).fill('coral')
svg.addCircle().cx(150).cy(60).r(30).fill('steelblue')
svg.addCircle().cx(240).cy(60).r(30).fill('seagreen')

String pretty = SvgFormatter.prettify(svg, [indent: '  '])
File prettyFile = ExampleSupport.outputDir().resolve('export-pretty.svg').toFile()
prettyFile.text = pretty

Svg optimized = SvgOptimizer.optimize(svg, [removeComments: true, precision: 2])
File optimizedFile = ExampleSupport.outputDir().resolve('export-optimized.svg').toFile()
SvgRenderer.toSvgFile(optimized, optimizedFile)

File pngFile = ExampleSupport.outputDir().resolve('export.png').toFile()
SvgRenderer.toPng(svg, pngFile, [width: 600, height: 240, antialiasing: true])

File jpegFile = ExampleSupport.outputDir().resolve('export.jpg').toFile()
SvgRenderer.toJpeg(svg, jpegFile, [width: 600, height: 240, backgroundColor: 'white', quality: 0.9])

println "wrote ${pngFile.name} and ${jpegFile.name}"
