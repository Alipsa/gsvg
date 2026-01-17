@Grab('se.alipsa.groovy:gsvg:1.0.0')
@Grab('se.alipsa.groovy:gsvg-export:1.0.0')

import se.alipsa.groovy.svg.AbstractElementContainer
import se.alipsa.groovy.svg.G
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.SvgElement
import se.alipsa.groovy.svg.templates.Template
import se.alipsa.groovy.svg.export.SvgRenderer
import examples.shared.ExampleSupport

class BadgeTemplate extends Template {
  @Override
  SvgElement apply(AbstractElementContainer parent, Map params) {
    Map merged = mergeParams(params)
    G group = parent.addG()
    group.addCircle().cx(merged.cx as Number).cy(merged.cy as Number).r(merged.r as Number).fill(merged.fill as String)
    group.addText(merged.label as String)
      .x(merged.cx as Number)
      .y((merged.cy as Number) + 5)
      .textAnchor('middle')
      .fontSize(12)
    return group
  }

  @Override
  Map getDefaults() {
    return [cx: 40, cy: 40, r: 20, fill: 'lightblue', label: 'A']
  }
}

Svg svg = new Svg(200, 120)
BadgeTemplate badge = new BadgeTemplate()

badge.apply(svg, [cx: 50, cy: 60, fill: 'gold', label: 'Gold'])
badge.apply(svg, [cx: 140, cy: 60, fill: 'tomato', label: 'Red'])

File outputFile = ExampleSupport.outputDir().resolve('clone-custom-template.svg').toFile()
SvgRenderer.toSvgFile(svg, outputFile)
