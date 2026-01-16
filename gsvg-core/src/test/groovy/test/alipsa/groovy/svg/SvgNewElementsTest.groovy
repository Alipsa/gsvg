package test.alipsa.groovy.svg

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.io.SvgReader

import static org.junit.jupiter.api.Assertions.assertEquals

class SvgNewElementsTest {

  @Test
  void parseSvgWithNewElements() {
    String svgContent = '<svg xmlns="http://www.w3.org/2000/svg">' +
        '<defs>' +
        '<color-profile name="sRGB"/>' +
        '<cursor id="cur1"/>' +
        '<solidcolor id="sc1"/>' +
        '<font id="f1">' +
          '<font-face/>' +
          '<glyph unicode="a"/>' +
          '<missing-glyph/>' +
          '<hkern/>' +
          '<vkern/>' +
        '</font>' +
        '<meshGradient id="mg"><meshrow><meshpatch/></meshrow></meshGradient>' +
        '<hatch id="h1"><hatchpath/></hatch>' +
        '</defs>' +
        '<use href="#f1"/>' +
        '<discard/>' +
        '<audio/>' +
        '<video/>' +
        '</svg>'

    Svg svg = SvgReader.parse(svgContent)
    assertEquals(svgContent, svg.toXml())
  }
}
