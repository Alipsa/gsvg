import groovy.transform.Field
import groovy.transform.SourceURI
@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

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

Svg svg = new Svg(260, 200)

int startX = 30
int startY = 30
int gap = 40

(0..<3).each { row ->
  (0..<4).each { col ->
    int x = startX + (col * gap)
    int y = startY + (row * gap)
    svg.addCircle().cx(x).cy(y).r(12).fill('steelblue')
  }
}

File outputFile = exampleSupport.outputFile('usecase-icon-grid.svg')
SvgRenderer.toSvgFile(svg, outputFile)
