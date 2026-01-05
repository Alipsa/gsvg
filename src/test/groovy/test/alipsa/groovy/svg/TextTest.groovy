package test.alipsa.groovy.svg

import se.alipsa.groovy.svg.Svg

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
        .getParent(Text).addContent('!')

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

  @Test
  void testTextAttributes() {
    Svg svg = new Svg()
    def text = svg.addText('Test')
      .x(10).y(20)
      .dx(5).dy(10)
      .fontSize(16)
      .fontFamily('Arial')
      .textAnchor('middle')
      .rotate(45)
      .textLength(100)
      .lengthAdjust('spacingAndGlyphs')
      .transform('translate(10, 20)')
      .baselineShift('super')
      .dominantBaseline('middle')
      .textDecoration('underline')
      .wordSpacing('5')
      .letterSpacing('2')

    assertEquals('10', text.getAttribute('x'))
    assertEquals('20', text.getAttribute('y'))
    assertEquals('5', text.getAttribute('dx'))
    assertEquals('10', text.getAttribute('dy'))
    assertEquals('16', text.getAttribute('font-size'))
    assertEquals('Arial', text.fontFamily)
    assertEquals('middle', text.textAnchor)
    assertEquals('45', text.rotate)
    assertEquals('100', text.textLength)
    assertEquals('spacingAndGlyphs', text.lengthAdjust)
    assertEquals('translate(10, 20)', text.transform)
    assertEquals('super', text.baselineShift)
    assertEquals('middle', text.dominantBaseline)
    assertEquals('underline', text.textDecoration)
    assertEquals('5', text.wordSpacing)
    assertEquals('2', text.letterSpacing)
  }

  @Test
  void testTextWithMultipleTspans() {
    Svg svg = new Svg()
    def text = svg.addText('Start ')
      .x(10).y(20)
      .fontSize(14)

    text.addTspan('Bold').fill('red')
    text.addTspan(' Normal ')
    text.addTspan('Italic').fill('blue')

    assertTrue(text.toXml().contains('<tspan'))
    assertTrue(text.toXml().contains('Bold'))
    assertTrue(text.toXml().contains('Italic'))
  }

  @Test
  void testTextWithMapAttributes() {
    Svg svg = new Svg()
    def text = svg.addText([x: '50', y: '100', fill: 'blue', 'font-size': '20'])

    assertEquals('50', text.getAttribute('x'))
    assertEquals('100', text.getAttribute('y'))
    assertEquals('blue', text.getAttribute('fill'))
    assertEquals('20', text.getAttribute('font-size'))
  }

  @Test
  void testTspanAttributes() {
    Svg svg = new Svg()
    def text = svg.addText('Base')
    def tspan = text.addTspan('Span')
      .x(30).y(40)
      .dx(5).dy(10)
      .fill('green')

    assertEquals('30', tspan.getAttribute('x'))
    assertEquals('40', tspan.getAttribute('y'))
    assertEquals('5', tspan.getAttribute('dx'))
    assertEquals('10', tspan.getAttribute('dy'))
    assertEquals('green', tspan.getAttribute('fill'))
  }

  @Test
  void testTextPathInText() {
    Svg svg = new Svg()
    def text = svg.addText().x(0).y(0)
    def textPath = text.addTextPath()
      .href('#myPath')
      .startOffset('25%')

    textPath.addContent('Text on path')

    assertTrue(text.toXml().contains('<textPath'))
    assertTrue(text.toXml().contains('href="#myPath"'))
    assertTrue(text.toXml().contains('startOffset="25%"'))
  }
}
