package test.alipsa.groovy.svg

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.Tspan

import static org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Text

class TextTest {

  @Test
  void testSimpleText() {
    Svg svg = new Svg(400, 200)
    Text text = svg.addText('Hello world!')
    .x(5)
    .y(15)
    .fill('red')
    assertEquals('<text xmlns="http://www.w3.org/2000/svg" x="5" y="15" fill="red">Hello world!</text>', text.toXml())
  }

  @Test
  void testComplexContent() {
    Svg svg = new Svg(400, 200)
    Text text = svg.addText('Hello ')
        .x(5)
        .y(30)
        .fill('red')
        .fontSize(35)
        .addTspan('World')
          .fill('none')
          .stroke('green')
        .getParent(Text).addText('!')

    assertEquals("""<text xmlns="http://www.w3.org/2000/svg" x="5" y="30" fill="red" font-size="35">Hello <tspan fill="none" stroke="green">World</tspan>!</text>""",
        text.toXml())
  }

  @Test
  void testTextPath() {

    // TODO make this example possible
    """<svg height="200" width="350" xmlns="http://www.w3.org/2000/svg">
  <path id="lineAC" d="M 30 180 q 150 -250 300 0" stroke="blue" stroke-width="2" fill="none"/>
  <text style="fill:red;font-size:25px;">
    <textPath href="#lineAC" startOffset="80">I love SVG! I love SVG!</textPath>
  </text>
</svg>"""
  }
}
