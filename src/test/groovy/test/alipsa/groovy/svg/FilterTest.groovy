package test.alipsa.groovy.svg

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.FeGaussianBlur
import se.alipsa.groovy.svg.Filter
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.SvgReader
import se.alipsa.groovy.svg.SvgWriter

import static org.junit.jupiter.api.Assertions.*

class FilterTest {

  @Test
  void testFilterWithGaussianBlur() {
    String svgContent = '''
    <svg xmlns="http://www.w3.org/2000/svg" width="100" height="100">
      <defs>
        <filter id="f1" x="0" y="0">
          <feGaussianBlur in="SourceGraphic" stdDeviation="15"/>
        </filter>
      </defs>
      <rect width="90" height="90" fill="red" filter="url(#f1)"/>
    </svg>
    '''.stripIndent()

    Svg svg = SvgReader.parse(svgContent)
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))
    checkGaussianFilterContent(svg)

    svg = new Svg(100, 100)
    svg.addDefs()
      .addFilter('f1').x(0).y(0)
      .addFeGaussianBlur().in('SourceGraphic').stdDeviation(15)
    svg.addRect(90, 90).fill('red').filter('url(#f1)')
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))
    checkGaussianFilterContent(svg)
  }

  void checkGaussianFilterContent(Svg svg) {
    Filter filter = svg[0][0]
    assertEquals('f1', filter.id)
    assertEquals('0', filter.x)
    assertEquals('0', filter.y)
    FeGaussianBlur feGaussianBlur = filter[0]
    assertEquals('SourceGraphic', feGaussianBlur.in)
    assertEquals('15', feGaussianBlur.stdDeviation)
  }
}
