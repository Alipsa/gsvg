package test.alipsa.groovy.svg

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Ellipse
import se.alipsa.groovy.svg.LinearGradient
import se.alipsa.groovy.svg.RadialGradient
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.io.SvgReader
import se.alipsa.groovy.svg.io.SvgWriter

import static org.junit.jupiter.api.Assertions.*

class GradientTest {

  @Test
  void testLinearGradient() {
    String svgContent = '''
    <svg xmlns="http://www.w3.org/2000/svg" height="150" width="400">
      <defs>
        <linearGradient id="grad1" x1="0%" x2="100%" y1="0%" y2="0%">
          <stop offset="0%" stop-color="yellow"/>
          <stop offset="100%" stop-color="red"/>
        </linearGradient>
      </defs>
      <ellipse cx="100" cy="70" rx="85" ry="55" fill="url(#grad1)"/>
    </svg>
    '''.stripIndent()

    Svg svg = SvgReader.parse(svgContent)
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))
    checkLinearContent(svg)

    svg = new Svg(400, 150)
    LinearGradient lg = svg.addDefs().addLinearGradient()
      .id('grad1')
      .x1('0%')
      .x2('100%')
      .y1('0%')
      .y2('0%')
    lg.addStop().offset('0%').stopColor('yellow')
    lg.addStop().offset('100%').stopColor('red')
    svg.addEllipse(85,55).cx(100).cy(70).fill('url(#grad1)')
    checkLinearContent(svg)
  }

  private void checkLinearContent(Svg svg) {
    LinearGradient lg = svg[0][0]
    assertEquals('150', svg.height)
    assertEquals('400', svg.width)
    assertEquals('grad1', lg.id)
    assertEquals('0%', lg.x1)
    assertEquals('100%', lg.x2)
    assertEquals('0%', lg.y1)
    assertEquals('0%', lg.y2)

    assertEquals('0%', lg.stops[0].offset)
    assertEquals('yellow', lg.stops[0].stopColor)
    assertEquals('100%', lg.stops[1].offset)
    assertEquals('red', lg.stops[1].stopColor)

    Ellipse ellipse = svg[1]
    assertEquals('85', ellipse.rx)
    assertEquals('55', ellipse.ry)
    assertEquals('100', ellipse.cx)
    assertEquals('70', ellipse.cy)
    assertEquals('url(#grad1)', ellipse.fill)
  }

  @Test
  void testRadialGradient() {
    String svgContent = '''
    <svg xmlns="http://www.w3.org/2000/svg" height="150" width="400">
      <defs>
        <radialGradient id="grad1" cx="50%" cy="50%" r="50%" fx="50%" fy="50%">
          <stop offset="0%" stop-color="red"/>
          <stop offset="100%" stop-color="blue"/>
        </radialGradient>
      </defs>
      <ellipse cx="100" cy="70" rx="85" ry="55" fill="url(#grad1)"/>
    </svg>
    '''.stripIndent()
    Svg svg = SvgReader.parse(svgContent)
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))
    checkRadialContent(svg)

    svg = new Svg(400, 150)
    RadialGradient lg = svg.addDefs().addRadialGradient()
        .id('grad1')
        .cx('50%')
        .cy('50%')
        .r('50%')
        .fx('50%')
        .fy('50%')
    lg.addStop().offset('0%').stopColor('red')
    lg.addStop().offset('100%').stopColor('blue')
    svg.addEllipse(85,55).cx(100).cy(70).fill('url(#grad1)')
    checkRadialContent(svg)
  }

  private void checkRadialContent(Svg svg) {
    RadialGradient rg = svg[0][0]
    assertEquals('150', svg.height)
    assertEquals('400', svg.width)
    assertEquals('grad1', rg.id)
    assertEquals('50%', rg.cx)
    assertEquals('50%', rg.cy)
    assertEquals('50%', rg.fx)
    assertEquals('50%', rg.fy)

    assertEquals('0%', rg.stops[0].offset)
    assertEquals('red', rg.stops[0].stopColor)
    assertEquals('100%', rg.stops[1].offset)
    assertEquals('blue', rg.stops[1].stopColor)

    Ellipse ellipse = svg[1]
    assertEquals('85', ellipse.rx)
    assertEquals('55', ellipse.ry)
    assertEquals('100', ellipse.cx)
    assertEquals('70', ellipse.cy)
    assertEquals('url(#grad1)', ellipse.fill)
  }

  @Test
  void testRadialGradientAttributes() {
    Svg svg = new Svg()
    def defs = svg.addDefs()
    def rg = defs.addRadialGradient()
      .id('rg1')
      .cx('60%').cy('40%')
      .r('75%')
      .fx('65%').fy('45%')
      .spreadMethod('reflect')
      .gradientUnits('userSpaceOnUse')

    assertEquals('rg1', rg.id)
    assertEquals('60%', rg.cx)
    assertEquals('40%', rg.cy)
    assertEquals('75%', rg.r)
    assertEquals('65%', rg.fx)
    assertEquals('45%', rg.fy)
    assertEquals('reflect', rg.spreadMethod)
    assertEquals('userSpaceOnUse', rg.gradientUnits)
  }

  @Test
  void testRadialGradientWithMapAttributes() {
    Svg svg = new Svg()
    def defs = svg.addDefs()
    def rg = defs.addRadialGradient([
      id: 'rg2',
      cx: '50%',
      cy: '50%',
      r: '50%'
    ])

    assertEquals('rg2', rg.id)
    assertEquals('50%', rg.cx)
    assertEquals('50%', rg.cy)
    assertEquals('50%', rg.r)
  }

  @Test
  void testLinearGradientAdditionalAttributes() {
    Svg svg = new Svg()
    def defs = svg.addDefs()
    def lg = defs.addLinearGradient()
      .id('lg2')
      .x1(0).y1(0)
      .x2(100).y2(100)
      .gradientUnits('userSpaceOnUse')
      .spreadMethod('pad')
      .gradientTransform('rotate(45)')

    assertEquals('lg2', lg.id)
    assertEquals('0', lg.x1)
    assertEquals('0', lg.y1)
    assertEquals('100', lg.x2)
    assertEquals('100', lg.y2)
    assertEquals('userSpaceOnUse', lg.gradientUnits)
    assertEquals('pad', lg.spreadMethod)
    assertEquals('rotate(45)', lg.gradientTransform)
  }

  @Test
  void testGradientStops() {
    Svg svg = new Svg()
    def defs = svg.addDefs()
    def lg = defs.addLinearGradient().id('multiStop')

    lg.addStop().offset('0%').stopColor('red')
    lg.addStop().offset('33%').stopColor('green')
    lg.addStop().offset('66%').stopColor('blue')
    lg.addStop().offset('100%').stopColor('yellow')

    assertEquals(4, lg.stops.size())
    assertEquals('red', lg.stops[0].stopColor)
    assertEquals('green', lg.stops[1].stopColor)
    assertEquals('blue', lg.stops[2].stopColor)
    assertEquals('yellow', lg.stops[3].stopColor)
  }

  @Test
  void testPropertyStyleAttributeAccess() {
    Svg svg = new Svg()
    def defs = svg.addDefs()
    def lg = defs.addLinearGradient().id('propTest')

    // Test property-style setter for attributes without explicit setters
    lg.gradientUnits = 'userSpaceOnUse'
    lg.spreadMethod = 'reflect'
    lg.gradientTransform = 'rotate(90)'

    // Verify using property-style getter
    assertEquals('userSpaceOnUse', lg.gradientUnits)
    assertEquals('reflect', lg.spreadMethod)
    assertEquals('rotate(90)', lg.gradientTransform)

    // Verify it actually set the attributes (not just properties)
    assertTrue(lg.toXml().contains('gradientUnits="userSpaceOnUse"'))
    assertTrue(lg.toXml().contains('spreadMethod="reflect"'))
    assertTrue(lg.toXml().contains('gradientTransform="rotate(90)"'))

    // Test that existing property setters still work (not overridden)
    lg.x1 = '10'
    lg.y1 = '20'
    assertEquals('10', lg.x1)
    assertEquals('20', lg.y1)
  }
}
