package test.alipsa.groovy.svg

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.SvgReader
import se.alipsa.groovy.svg.SvgWriter

import static org.junit.jupiter.api.Assertions.assertEquals

class MaskTest {

  @Test
  void testMask() {
    String svgContent = '''
    <svg xmlns="http://www.w3.org/2000/svg" viewBox="-10 -10 120 120">
      <rect x="-10" y="-10" width="120" height="120" fill="blue"/>
      <mask id="myMask">
        <rect x="0" y="0" width="100" height="100" fill="white"/>
        <path d="M10,35 A20,20,0,0,1,50,35 A20,20,0,0,1,90,35 Q90,65,50,95 Q10,65,10,35 Z" fill="black"/>
      </mask>
      <polygon points="-10,110 110,110 110,-10" fill="orange"/>
      <circle cx="50" cy="50" r="50" fill="purple" mask="url(#myMask)"/>
    </svg>
    '''.stripIndent()

    Svg svg = SvgReader.parse(svgContent)
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))

    svg = new Svg().viewBox("-10 -10 120 120")
    svg.addRect().x(-10).y(-10).width(120).height(120).fill('blue')
    def myMask = svg.addMask('myMask')
    myMask.addRect().x(0).y(0).width(100).height(100).fill('white')
    myMask.addPath().d("M10,35 A20,20,0,0,1,50,35 A20,20,0,0,1,90,35 Q90,65,50,95 Q10,65,10,35 Z").fill('black')
    svg.addPolygon("-10,110 110,110 110,-10").fill('orange')
    svg.addCircle().cx(50).cy(50).r(50).fill("purple").mask("url(#myMask)")
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))
  }
}
