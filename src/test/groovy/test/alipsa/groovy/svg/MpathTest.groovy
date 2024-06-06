package test.alipsa.groovy.svg

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Path
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.SvgReader
import se.alipsa.groovy.svg.SvgWriter

import static org.junit.jupiter.api.Assertions.assertEquals

class MpathTest {

  @Test
  void testMpath() {
    String svgContent = '''
      <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="100%" height="100%" viewBox="0 0 500 300">
        <path id="path1" d="M100,250 C 100,50 400,50 400,250" fill="none" stroke="blue" stroke-width="7.06"/>
        <circle cx="100" cy="250" r="17.64" fill="blue"/>
        <circle cx="250" cy="100" r="17.64" fill="blue"/>
        <circle cx="400" cy="250" r="17.64" fill="blue"/>      
        <path d="M-25,-12.5 L25,-12.5 L 0,-87.5 z" fill="yellow" stroke="red" stroke-width="7.06">
          <animateMotion dur="6s" repeatCount="indefinite" rotate="auto">
            <mpath xlink:href="#path1"/>
          </animateMotion>
        </path>
      </svg>
      '''.stripIndent()
    Svg svg = SvgReader.parse(svgContent)
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))

    svg = new Svg().xlink().width('100%').height('100%').viewBox("0 0 500 300")
    svg.addPath("path1").d("M100,250 C 100,50 400,50 400,250").fill('none').stroke('blue').strokeWidth(7.06)
    svg.addCircle().cx(100).cy(250).r(17.64).fill('blue')
    svg.addCircle().cx(250).cy(100).r(17.64).fill('blue')
    svg.addCircle().cx(400).cy(250).r(17.64).fill('blue')
    Path p = svg.addPath().d("M-25,-12.5 L25,-12.5 L 0,-87.5 z").fill('yellow').stroke('red').strokeWidth(7.06)
    p.addAnimateMotion().dur("6s").repeatCount('indefinite').rotate('auto')
    .addMpath().xlinkHref('#path1')
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))
  }
}
