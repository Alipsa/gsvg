package test.alipsa.groovy.svg

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Circle
import se.alipsa.groovy.svg.Pattern
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.SvgReader
import se.alipsa.groovy.svg.SvgWriter

import static org.junit.jupiter.api.Assertions.*

class PatternTest {

  @Test
  void testSimplePattern() {
    String svgContent = '''
      <svg xmlns="http://www.w3.org/2000/svg" width="400" height="110">
        <defs>
          <pattern id="patt1" x="0" y="0" width="20" height="20" patternUnits="userSpaceOnUse">
            <circle cx="10" cy="10" r="10" fill="red"/>
          </pattern>
        </defs>
        <rect width="200" height="100" x="0" y="0" stroke="black" fill="url(#patt1)"/>
      </svg>
      '''.stripIndent()

    Svg svg = SvgReader.parse(svgContent)
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))
    checkSimpleContent(svg)

    svg = new Svg(400, 110)
    def pattern = svg.addDefs().addPattern('patt1')
        .x(0).y(0).width(20).height(20).patternUnits('userSpaceOnUse')
    pattern.addCircle()
      .cx(10).cy(10).r(10).fill('red')
    svg.addRect(200, 100)
      .x(0).y(0).stroke('black').fill('url(#patt1)')
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))
    checkSimpleContent(svg)
  }

  static void checkSimpleContent(Svg svg) {
    Pattern pattern = svg[0][0] as Pattern
    assertNotNull(pattern)
    assertEquals(Pattern, pattern.class)
    assertEquals('patt1', pattern.id)
    assertEquals('0', pattern.x)
    assertEquals('0', pattern.y)
    assertEquals('20', pattern.width)
    assertEquals('20', pattern.height)
    assertEquals('userSpaceOnUse', pattern.patternUnits)
    assertEquals(1, pattern.children.size())
    assertEquals(Circle, pattern[0].class)
  }
}
