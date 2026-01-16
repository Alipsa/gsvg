package test.alipsa.groovy.svg

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.Symbol
import se.alipsa.groovy.svg.io.SvgReader
import se.alipsa.groovy.svg.io.SvgWriter

import static org.junit.jupiter.api.Assertions.*

class SymbolTest {

  @Test
  void testSymbol() {
    String svgContent = '''
      <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 0 35 20">
        <symbol id="myDot" width="10" height="10" viewBox="0 0 2 2">
          <circle cx="1" cy="1" r="1"/>
        </symbol>
        <path d="M0,10 h34 M10,0 v20 M25,0 v20" fill="none" stroke="pink"/>
        <use xlink:href="#myDot" x="5" y="5" style="opacity:1.0"/>
        <use xlink:href="#myDot" x="20" y="5" style="opacity:0.2"/>
      </svg>
      '''.stripIndent()
    Svg svg = SvgReader.parse(svgContent)
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))

    svg = new Svg().xlink().viewBox("0 0 35 20")
    svg.addSymbol("myDot").width(10).height(10).viewBox("0 0 2 2")
      .addCircle().cx(1).cy(1).r(1)
    svg.addPath().d("M0,10 h34 M10,0 v20 M25,0 v20").fill('none').stroke('pink')
    svg.addUse().xlinkHref("#myDot").x(5).y(5).style("opacity:1.0")
    svg.addUse().xlinkHref("#myDot").x(20).y(5).style("opacity:0.2")
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))
  }

  @Test
  void testSymbolPositionNumber() {
    Svg svg = new Svg()
    Symbol symbol = svg.addSymbol('icon')
            .x(10)
            .y(20)

    assertEquals('10', symbol.x)
    assertEquals('10', symbol.getX())
    assertEquals('20', symbol.y)
    assertEquals('20', symbol.getY())

    String xml = symbol.toXml()
    assertTrue(xml.contains('x="10"'))
    assertTrue(xml.contains('y="20"'))
  }

  @Test
  void testSymbolPositionString() {
    Svg svg = new Svg()
    Symbol symbol = svg.addSymbol('icon')
            .x('5%')
            .y('10%')

    assertEquals('5%', symbol.x)
    assertEquals('10%', symbol.y)

    String xml = symbol.toXml()
    assertTrue(xml.contains('x="5%"'))
    assertTrue(xml.contains('y="10%"'))
  }

  @Test
  void testSymbolRefPointNumber() {
    Svg svg = new Svg()
    Symbol symbol = svg.addSymbol('icon')
            .refX(5)
            .refY(5)

    assertEquals('5', symbol.refX)
    assertEquals('5', symbol.getRefX())
    assertEquals('5', symbol.refY)
    assertEquals('5', symbol.getRefY())

    String xml = symbol.toXml()
    assertTrue(xml.contains('refX="5"'))
    assertTrue(xml.contains('refY="5"'))
  }

  @Test
  void testSymbolRefPointString() {
    Svg svg = new Svg()
    Symbol symbol = svg.addSymbol('icon')
            .refX('center')
            .refY('middle')

    assertEquals('center', symbol.refX)
    assertEquals('middle', symbol.refY)
  }

  @Test
  void testSymbolPreserveAspectRatio() {
    Svg svg = new Svg()
    Symbol symbol = svg.addSymbol('icon')
            .viewBox('0 0 100 100')
            .preserveAspectRatio('xMidYMid meet')

    assertEquals('xMidYMid meet', symbol.preserveAspectRatio)
    assertEquals('xMidYMid meet', symbol.getPreserveAspectRatio())
    assertTrue(symbol.toXml().contains('preserveAspectRatio="xMidYMid meet"'))
  }

  @Test
  void testSymbolPreserveAspectRatioSlice() {
    Svg svg = new Svg()
    Symbol symbol = svg.addSymbol('icon')
            .viewBox('0 0 50 50')
            .preserveAspectRatio('xMinYMin slice')

    assertEquals('xMinYMin slice', symbol.preserveAspectRatio)
  }

  @Test
  void testSymbolCompleteAttributes() {
    Svg svg = new Svg()
    Symbol symbol = svg.addSymbol('completeIcon')
            .x(10)
            .y(20)
            .width(100)
            .height(100)
            .viewBox('0 0 24 24')
            .refX(12)
            .refY(12)
            .preserveAspectRatio('xMidYMid meet')

    assertEquals('10', symbol.x)
    assertEquals('20', symbol.y)
    assertEquals('100', symbol.width)
    assertEquals('100', symbol.height)
    assertEquals('0 0 24 24', symbol.viewBox)
    assertEquals('12', symbol.refX)
    assertEquals('12', symbol.refY)
    assertEquals('xMidYMid meet', symbol.preserveAspectRatio)
  }

  @Test
  void testSymbolWithComplexContent() {
    Svg svg = new Svg()
    Symbol symbol = svg.addSymbol('shieldIcon')
            .viewBox('0 0 24 24')

    // Add complex path
    symbol.addPath()
            .d('M12 2L2 7v10c0 5.5 3.8 10.7 10 12 6.2-1.3 10-6.5 10-12V7l-10-5z')
            .fill('blue')

    // Add circle overlay
    symbol.addCircle()
            .cx(12)
            .cy(12)
            .r(3)
            .fill('white')

    assertEquals(2, symbol.children.size())
  }

  @Test
  void testSymbolWithNestedGroups() {
    Svg svg = new Svg()
    Symbol symbol = svg.addSymbol('complexIcon')
            .viewBox('0 0 100 100')

    symbol.addG()
            .addRect().x(10).y(10).width(30).height(30).fill('red')

    symbol.addG()
            .addCircle().cx(70).cy(70).r(15).fill('blue')

    assertEquals(2, symbol.children.size())
    assertTrue(symbol.toXml().contains('<g'))
  }

  @Test
  void testSymbolMethodChaining() {
    Svg svg = new Svg()
    Symbol symbol = svg.addSymbol('chainTest')

    Symbol result = symbol
            .x(0)
            .y(0)
            .width(50)
            .height(50)
            .viewBox('0 0 50 50')
            .refX(25)
            .refY(25)
            .preserveAspectRatio('xMidYMid meet')

    assertSame(symbol, result)
  }

  @Test
  void testSymbolReusableGraphic() {
    // Real-world scenario: Define reusable icon symbol
    Svg svg = new Svg(200, 200)

    // Define the symbol
    svg.addSymbol('starIcon')
            .viewBox('0 0 100 100')
            .addPath()
            .d('M50,10 L61,35 L90,35 L67,52 L78,77 L50,60 L22,77 L33,52 L10,35 L39,35 Z')
            .fill('gold')
            .stroke('black')
            .strokeWidth(2)

    // Use the symbol multiple times
    svg.addUse().href('#starIcon').x(10).y(10)
    svg.addUse().href('#starIcon').x(70).y(70)
    svg.addUse().href('#starIcon').x(140).y(10)

    String xml = svg.toXml()
    assertTrue(xml.contains('<symbol'))
    assertTrue(xml.contains('id="starIcon"'))
    assertEquals(3, xml.split('<use').length - 1) // Count use elements
  }

  @Test
  void testSymbolWidthHeightGetters() {
    Svg svg = new Svg()
    Symbol symbol = svg.addSymbol('test')
            .width(100)
            .height(200)

    assertEquals('100', symbol.width)
    assertEquals('100', symbol.getWidth())
    assertEquals('200', symbol.height)
    assertEquals('200', symbol.getHeight())
  }

  @Test
  void testSymbolViewBoxGetter() {
    Svg svg = new Svg()
    Symbol symbol = svg.addSymbol('test')
            .viewBox('0 0 150 150')

    assertEquals('0 0 150 150', symbol.viewBox)
    assertEquals('0 0 150 150', symbol.getViewBox())
  }
}
