package test.alipsa.groovy.svg

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Animate
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.io.SvgReader
import se.alipsa.groovy.svg.io.SvgWriter

import static org.junit.jupiter.api.Assertions.*

class AnimateTest {

  @Test
  void testSimpleAnimation() {
    String svgContent = '''
    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 10 10">
      <rect width="10" height="10">
        <animate attributeName="rx" values="0;5;0" dur="10s" repeatCount="indefinite"/>
      </rect>
    </svg>
    '''.stripIndent()

    Svg svg = SvgReader.parse(svgContent)
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))
    checkSimpleAnimateContent(svg)

    svg = new Svg().viewBox('0 0 10 10')
      .addRect(10, 10)
      .addAnimate()
        .attributeName('rx')
        .values('0;5;0')
        .dur('10s')
        .repeatCount('indefinite')
      .parent.parent
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))
    checkSimpleAnimateContent(svg)
  }

  void checkSimpleAnimateContent(Svg svg) {
    Animate animate = svg[0].animations[0] as Animate
    assertEquals('rx', animate.attributeName)
    assertEquals('0;5;0', animate.values)
    assertEquals('indefinite', animate.repeatCount)
  }
}
