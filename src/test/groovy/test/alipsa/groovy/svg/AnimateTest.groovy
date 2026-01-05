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

  @Test
  void testAnimateAttributes() {
    Svg svg = new Svg()
    def rect = svg.addRect().width(100).height(100)
    def animate = rect.addAnimate()
      .attributeName('fill')
      .from('red')
      .to('blue')
      .dur('3s')
      .begin('1s')
      .repeatCount('2')
      .fill('freeze')

    assertEquals('fill', animate.attributeName)
    assertEquals('red', animate.from)
    assertEquals('blue', animate.to)
    assertEquals('3s', animate.dur)
    assertEquals('1s', animate.begin)
    assertEquals('2', animate.repeatCount)
    assertEquals('freeze', animate.fill)
  }

  @Test
  void testAnimateWithValues() {
    Svg svg = new Svg()
    def circle = svg.addCircle().cx(50).cy(50).r(10)
    def animate = circle.addAnimate()
      .attributeName('r')
      .values('10;20;15;10')
      .dur('2s')
      .repeatCount('indefinite')

    assertEquals('r', animate.attributeName)
    assertEquals('10;20;15;10', animate.values)
    assertEquals('2s', animate.dur)
  }

  @Test
  void testAnimateWithMapAttributes() {
    Svg svg = new Svg()
    def rect = svg.addRect()
    def animate = rect.addAnimate([
      attributeName: 'opacity',
      from: '1',
      to: '0',
      dur: '1s',
      repeatCount: 'indefinite'
    ])

    assertEquals('opacity', animate.attributeName)
    assertEquals('1', animate.from)
    assertEquals('0', animate.to)
    assertEquals('1s', animate.dur)
    assertEquals('indefinite', animate.repeatCount)
  }

  @Test
  void testMultipleAnimations() {
    Svg svg = new Svg()
    def rect = svg.addRect().x(0).y(0).width(50).height(50)

    rect.addAnimate().attributeName('x').from('0').to('100').dur('2s')
    rect.addAnimate().attributeName('fill').from('red').to('blue').dur('2s')

    def animations = rect.animations
    assertEquals(2, animations.size())
    assertEquals('x', animations[0].attributeName)
    assertEquals('fill', animations[1].attributeName)
  }
}
