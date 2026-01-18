import groovy.transform.Field
import groovy.transform.SourceURI

@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import se.alipsa.groovy.svg.Circle
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.export.SvgRenderer

@SourceURI
@Field
URI scriptUri

File scriptDir = new File(scriptUri).parentFile
File helper = new File(scriptDir.parentFile.parentFile, 'helper.groovy')

if (!helper.exists()) {
  throw new IllegalStateException("Cannot find helper script at ${helper.absolutePath}")
}
def exampleSupport = evaluate(helper)
exampleSupport.scriptDir = scriptDir

Svg svg = new Svg(200, 140)

svg.addCircle().cx(30).cy(40).r(10).fill('orange')
svg.addCircle().cx(90).cy(40).r(25).fill('teal')
svg.addRect().x(20).y(80).width(60).height(40).fill('lightgray')

List<Circle> circles = svg.findAll(Circle)
List<Circle> largeCircles = svg.findAll(Circle) { (it.getR() as int) > 15 }

def descendants = svg.descendants()

def firstOrange = svg.findFirst { it.getAttribute('fill') == 'orange' }

println "circles=${circles.size()} largeCircles=${largeCircles.size()} descendants=${descendants.size()} firstOrange=${firstOrange?.name}"
File outputFile = exampleSupport.outputFile('navigation-descendants-filter.svg')
SvgRenderer.toSvgFile(svg, outputFile)
