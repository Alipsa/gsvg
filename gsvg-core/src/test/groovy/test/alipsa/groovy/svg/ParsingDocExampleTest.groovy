package test.alipsa.groovy.svg

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Rect
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.io.SvgReader
import se.alipsa.groovy.svg.io.SvgWriter

import static org.junit.jupiter.api.Assertions.*

class ParsingDocExampleTest {

  @Test
  void parsingExampleMatchesDocs() {
    File source = new File('src/test/resources/logo.svg')
    Svg svg = SvgReader.parse(source)

    List<Rect> rects = svg[Rect]
    assertEquals(1, rects.size())

    rects.each { it.stroke('red').addAttribute('stroke-width', 2) }

    assertEquals('red', rects[0].stroke)
    assertEquals('2', rects[0].getAttribute('stroke-width'))

    String output = SvgWriter.toXmlPretty(svg)
    assertTrue(output.contains('stroke="red"'))
    assertTrue(output.contains('stroke-width="2"'))
  }
}
