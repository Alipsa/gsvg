package test.alipsa.groovy.svg

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.AnimateTransform
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.io.SvgReader
import se.alipsa.groovy.svg.io.SvgWriter

import static org.junit.jupiter.api.Assertions.assertEquals

class AnimateTransformTest {

  @Test
  void testAnimateMotion() {
    String svgContent = '''
    <svg xmlns="http://www.w3.org/2000/svg" width="200" height="180">
      <rect x="30" y="30" height="110" width="110" style="stroke:green;fill:red">
        <animateTransform attributeName="transform" begin="0s" dur="10s" type="rotate" from="0 85 85" to="360 85 85" repeatCount="indefinite"/>
      </rect>
    </svg>
    '''.stripIndent()
    Svg svg = SvgReader.parse(svgContent)
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))
    checkSimpleAnimateTransformContent(svg)

    svg = new Svg(200, 180)
    svg.addRect()
      .x(30)
      .y(30)
      .height(110)
      .width(110)
      .style('stroke:green;fill:red')
    .addAnimateTransform()
      .attributeName('transform')
      .begin('0s')
      .dur('10s')
      .type('rotate')
      .from('0 85 85')
      .to('360 85 85')
      .repeatCount('indefinite')
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))
    checkSimpleAnimateTransformContent(svg)
  }

  void checkSimpleAnimateTransformContent(Svg svg) {
    AnimateTransform t = svg[0].animation
    assertEquals('transform', t.attributeName)
    assertEquals('0s', t.begin)
    assertEquals('10s', t.dur)
    assertEquals('rotate', t.type)
    assertEquals('0 85 85', t.from)
    assertEquals('360 85 85', t.to)
    assertEquals('indefinite', t.repeatCount)
  }
}
