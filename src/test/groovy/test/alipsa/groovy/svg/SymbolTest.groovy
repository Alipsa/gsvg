package test.alipsa.groovy.svg

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.SvgReader
import se.alipsa.groovy.svg.SvgWriter

import static org.junit.jupiter.api.Assertions.assertEquals

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
}
