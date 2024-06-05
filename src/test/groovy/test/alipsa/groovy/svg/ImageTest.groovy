package test.alipsa.groovy.svg

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.SvgReader
import se.alipsa.groovy.svg.SvgWriter

import static org.junit.jupiter.api.Assertions.assertEquals

class ImageTest {

  @Test
  void testRecursiveImage() {
    String svgContent = '''
      <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="5cm" height="3cm" viewBox="0 0 50 30">
        <title>Recursive SVG</title>
        <style type="text/css">#target:target { fill: red;}</style>
        <circle id="target" stroke="red" stroke-width="5" fill="none" cx="50%" cy="50%" r="12"/>
        <image xlink:href="#target" x="40%" y="40%" width="20%" height="20%"/>
      </svg>
      '''.stripIndent()

    Svg svg = SvgReader.parse(svgContent)
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))

    svg = new Svg('5cm', '3cm').viewBox("0 0 50 30").xlink()
    svg.addTitle('Recursive SVG')
    svg.addStyle().type("text/css").addContent('#target:target { fill: red;}')
    svg.addCircle("target")
        .stroke("red").strokeWidth(5).fill('none')
        .cx('50%').cy('50%').r(12)
    svg.addImage().xlinkHref("#target").x('40%').y('40%').width('20%').height('20%')
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))
  }
}
