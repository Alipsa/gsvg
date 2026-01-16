package test.alipsa.groovy.svg

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.AnimateMotion
import se.alipsa.groovy.svg.Rect
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.io.SvgReader

import static org.junit.jupiter.api.Assertions.assertEquals

class AnimateMotionTest {

  @Test
  void testAnimateMotion() {
    String svgContent = '<svg xmlns="http://www.w3.org/2000/svg" width="100%" height="150">' +
        '<rect x="45" y="18" width="155" height="45" style="stroke:green;fill:none;">' +
        '<animateMotion path="M0,0 q60,100 100,0 q60,-20 100,0" begin="0s" dur="10s" repeatCount="indefinite"/>' +
      '</rect>' +
      '''<text x="50" y="50" style="font-family:Verdana;font-size:32">It's SVG!''' +
        '<animateMotion path="M0,0 q60,100 100,0 q60,-20 100,0" begin="0s" dur="10s" repeatCount="indefinite"/>' +
        '</text>' +
    '</svg>'
    Svg svg = SvgReader.parse(svgContent)
    assertEquals(svgContent, svg.toXml())
    checkSimpleAnimateMotionContent(svg)

    svg = new Svg()
    svg.width('100%').height(150)
    Rect rect = svg.addRect().x(45).y(18).width(155).height(45).style('stroke:green;fill:none;')
    rect.addAnimateMotion()
        .path('M0,0 q60,100 100,0 q60,-20 100,0')
        .begin('0s')
        .dur('10s')
        .repeatCount('indefinite')
    svg.addText("It's SVG!").x(50).y(50).style('font-family:Verdana;font-size:32')
      .addAnimateMotion()
        .path('M0,0 q60,100 100,0 q60,-20 100,0')
        .begin('0s')
        .dur('10s')
        .repeatCount('indefinite')
    assertEquals(svgContent, svg.toXml())
    checkSimpleAnimateMotionContent(svg)
  }

  static void checkSimpleAnimateMotionContent(Svg svg) {
    AnimateMotion rectMotion = svg[0].animations[0]
    assertEquals('M0,0 q60,100 100,0 q60,-20 100,0', rectMotion.path)
    assertEquals('0s', rectMotion.begin)
    assertEquals('10s', rectMotion.dur)
    assertEquals('indefinite', rectMotion.repeatCount)

    AnimateMotion textMotion = svg[1].animation
    assertEquals('M0,0 q60,100 100,0 q60,-20 100,0', textMotion.path)
    assertEquals('0s', textMotion.begin)
    assertEquals('10s', textMotion.dur)
    assertEquals('indefinite', textMotion.repeatCount)
  }
}
