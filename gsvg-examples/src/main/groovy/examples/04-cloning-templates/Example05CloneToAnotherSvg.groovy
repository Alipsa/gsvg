@Grab('se.alipsa.groovy:gsvg:1.0.0')

import se.alipsa.groovy.svg.Svg
import examples.shared.ExampleSupport

Svg source = new Svg(120, 120)
source.addCircle().cx(60).cy(60).r(30).fill('plum')

Svg target = new Svg(240, 120)
source.descendants().each { it.clone(target) }

ExampleSupport.writeSvg(target, 'clone-to-another-svg.svg')
