package test.alipsa.groovy.svg

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Ellipse
import se.alipsa.groovy.svg.LinearGradient
import se.alipsa.groovy.svg.RadialGradient
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.SvgReader
import se.alipsa.groovy.svg.SvgWriter

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
}
