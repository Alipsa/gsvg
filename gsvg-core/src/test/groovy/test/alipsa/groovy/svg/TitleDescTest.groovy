package test.alipsa.groovy.svg

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.io.SvgReader
import se.alipsa.groovy.svg.Svg

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertTrue

class TitleDescTest {

  @Test
  void testAddTitleAndDescStringAddChildren() {
    Svg svg = new Svg(100, 100)

    svg.addTitle('Chart')
    svg.addDesc('Example')

    assertNotNull(svg.getTitle())
    assertNotNull(svg.getDesc())
    assertEquals(2, svg.children.size())
  }

  @Test
  void testParseTitleAndDescAreAccessible() {
    String xml = '''<svg width="100" height="50" xmlns="http://www.w3.org/2000/svg">
  <title>Title here</title>
  <desc>Description here</desc>
</svg>'''

    Svg svg = SvgReader.parse(xml)

    assertNotNull(svg.getTitle())
    assertNotNull(svg.getDesc())
    assertTrue(svg.getTitle().element.getText().contains('Title here'))
    assertTrue(svg.getDesc().element.getText().contains('Description here'))
  }
}
