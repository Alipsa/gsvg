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

  @Test
  void testAnimateTimingAttributes() {
    Svg svg = new Svg()
    def rect = svg.addRect()
    def animate = rect.addAnimate()
      .attributeName('opacity')
      .from('1')
      .to('0')
      .begin('1s')
      .dur('2s')
      .end('5s')
      .repeatCount('3')
      .repeatDur('10s')

    // Test getters
    assertEquals('1s', animate.begin)
    assertEquals('1s', animate.getBegin())
    assertEquals('2s', animate.dur)
    assertEquals('2s', animate.getDur())
    assertEquals('5s', animate.end)
    assertEquals('5s', animate.getEnd())
    assertEquals('3', animate.repeatCount)
    assertEquals('3', animate.getRepeatCount())
    assertEquals('10s', animate.repeatDur)
    assertEquals('10s', animate.getRepeatDur())

    // Verify in XML
    String xml = animate.toXml()
    assertTrue(xml.contains('begin="1s"'))
    assertTrue(xml.contains('dur="2s"'))
    assertTrue(xml.contains('end="5s"'))
    assertTrue(xml.contains('repeatCount="3"'))
    assertTrue(xml.contains('repeatDur="10s"'))
  }

  @Test
  void testAnimateInterpolationModes() {
    Svg svg = new Svg()
    def rect = svg.addRect()

    // Test calcMode
    def anim1 = rect.addAnimate()
      .attributeName('x')
      .values('0;50;100')
      .calcMode('linear')
    assertEquals('linear', anim1.calcMode)
    assertEquals('linear', anim1.getCalcMode())
    assertTrue(anim1.toXml().contains('calcMode="linear"'))

    // Test with discrete
    def anim2 = rect.addAnimate()
      .attributeName('y')
      .values('0;50;100')
      .calcMode('discrete')
    assertEquals('discrete', anim2.calcMode)

    // Test with spline
    def anim3 = rect.addAnimate()
      .attributeName('width')
      .values('10;50;100')
      .calcMode('spline')
    assertEquals('spline', anim3.calcMode)
  }

  @Test
  void testAnimateAdditiveAndAccumulate() {
    Svg svg = new Svg()
    def rect = svg.addRect()

    // Test additive
    def anim1 = rect.addAnimate()
      .attributeName('x')
      .from('0')
      .to('100')
      .additive('sum')
    assertEquals('sum', anim1.additive)
    assertEquals('sum', anim1.getAdditive())
    assertTrue(anim1.toXml().contains('additive="sum"'))

    // Test accumulate
    def anim2 = rect.addAnimate()
      .attributeName('y')
      .from('0')
      .to('50')
      .accumulate('sum')
      .repeatCount('3')
    assertEquals('sum', anim2.accumulate)
    assertEquals('sum', anim2.getAccumulate())
    assertTrue(anim2.toXml().contains('accumulate="sum"'))
  }

  @Test
  void testAnimateKeyTimes() {
    Svg svg = new Svg()
    def circle = svg.addCircle()
    def animate = circle.addAnimate()
      .attributeName('r')
      .values('10;20;30;10')
      .keyTimes('0;0.3;0.7;1')
      .dur('4s')

    assertEquals('0;0.3;0.7;1', animate.keyTimes)
    assertEquals('0;0.3;0.7;1', animate.getKeyTimes())
    assertTrue(animate.toXml().contains('keyTimes="0;0.3;0.7;1"'))
  }

  @Test
  void testAnimateComplexConfiguration() {
    Svg svg = new Svg()
    def rect = svg.addRect()
    def animate = rect.addAnimate()
      .attributeName('width')
      .values('10;50;100;50;10')
      .dur('5s')
      .begin('0s')
      .end('30s')
      .repeatCount('indefinite')
      .calcMode('spline')
      .additive('sum')
      .accumulate('sum')
      .keyTimes('0;0.25;0.5;0.75;1')

    // Verify all attributes are set
    assertEquals('width', animate.attributeName)
    assertEquals('10;50;100;50;10', animate.values)
    assertEquals('5s', animate.dur)
    assertEquals('0s', animate.begin)
    assertEquals('30s', animate.end)
    assertEquals('indefinite', animate.repeatCount)
    assertEquals('spline', animate.calcMode)
    assertEquals('sum', animate.additive)
    assertEquals('sum', animate.accumulate)
    assertEquals('0;0.25;0.5;0.75;1', animate.keyTimes)

    // Verify in XML
    String xml = animate.toXml()
    assertTrue(xml.contains('attributeName="width"'))
    assertTrue(xml.contains('calcMode="spline"'))
    assertTrue(xml.contains('additive="sum"'))
    assertTrue(xml.contains('accumulate="sum"'))
    assertTrue(xml.contains('keyTimes="0;0.25;0.5;0.75;1"'))
  }
}
