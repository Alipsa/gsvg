package test.alipsa.groovy.svg

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.io.SvgReader

import static org.junit.jupiter.api.Assertions.assertEquals

class TextPathTest {

  @Test
  void testTextPAth() {
    String svgContent = '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 100 100">' +
        '<path id="MyPath" fill="none" stroke="red" d="M10,90 Q90,90 90,45 Q90,10 50,10 Q10,10 10,40 Q10,70 45,70 Q70,70 75,50"/>' +
        '<text>' +
          '<textPath href="#MyPath">Quick brown fox jumps over the lazy dog.</textPath>' +
        '</text>' +
      '</svg>'

    Svg svg = SvgReader.parse(svgContent)
    assertEquals(svgContent, svg.toXml())

    svg = new Svg().viewBox("0 0 100 100")
    svg.addPath('MyPath').fill('none').stroke('red').d("M10,90 Q90,90 90,45 Q90,10 50,10 Q10,10 10,40 Q10,70 45,70 Q70,70 75,50")
    svg.addText().addTextPath().href('#MyPath').addContent('Quick brown fox jumps over the lazy dog.')
    assertEquals(svgContent, svg.toXml())
  }
}
