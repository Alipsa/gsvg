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

Svg svg = new Svg(420, 300)

// Background
svg.addRect(0, 0, 420, 300).fill('#f8f8f8')

// Text with stroke and fill
svg.addText('Stroke + Fill')
  .x(20)
  .y(50)
  .fontSize(32)
  .fontFamily('Arial, sans-serif')
  .fill('gold')
  .stroke('darkgoldenrod')
  .strokeWidth(1)

// Text with opacity
svg.addText('Full opacity')
  .x(20)
  .y(110)
  .fontSize(28)
  .fontFamily('Arial, sans-serif')
  .fill('steelblue')
  .opacity(1.0)

svg.addText('Half opacity')
  .x(220)
  .y(110)
  .fontSize(28)
  .fontFamily('Arial, sans-serif')
  .fill('steelblue')
  .opacity(0.5)

// Text with translate transform
svg.addText('Translated')
  .x(0)
  .y(0)
  .fontSize(24)
  .fontFamily('Arial, sans-serif')
  .fill('coral')
  .translate(20, 160)

// Text with scale transform
svg.addText('Scaled')
  .x(200)
  .y(160)
  .fontSize(16)
  .fontFamily('Arial, sans-serif')
  .fill('seagreen')
  .scale(1.5)

// Text with rotate around center point
svg.addText('Rotated')
  .x(180)
  .y(240)
  .fontSize(20)
  .fontFamily('Arial, sans-serif')
  .fill('mediumpurple')
  .rotate(-15, 180, 240)

// Text combining stroke, opacity, and transform
svg.addText('Combined')
  .x(20)
  .y(280)
  .fontSize(30)
  .fontFamily('Arial, sans-serif')
  .fill('tomato')
  .stroke('darkred')
  .strokeWidth(0.5)
  .opacity(0.8)
  .translate(0, 0)
  .scale(1.2)

File outputFile = exampleSupport.outputFile('advanced-text-presentation.svg')
SvgRenderer.toSvgFile(svg, outputFile)
