package test.alipsa.groovy.svg

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.io.SvgReader
import se.alipsa.groovy.svg.io.SvgWriter

import static org.junit.jupiter.api.Assertions.assertEquals

class ViewTest {

  @Test
  void testView() {
    String svgContent = '''
      <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="600" height="200" viewBox="0 0 600 200">
        <circle r="50" cx="180" cy="50" fill="#8ca0ff"/>
        <view id="normalSizeView" viewBox="0 0 600 200"/>
        <view id="doubleSizeView" viewBox="0 0 300 100"/>     
        <a xlink:href="#normalSizeView">
          <text x="5" y="40" font-size="20">normal size</text>
        </a>
        <a xlink:href="#doubleSizeView">
          <text x="5" y="60" font-size="20">double size</text>
        </a>
      </svg>
      '''.stripIndent()

    Svg svg = SvgReader.parse(svgContent)
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))

    svg = new Svg().xlink().width(600).height(200).viewBox('0 0 600 200')
    svg.addCircle().r(50).cx(180).cy(50).fill("#8ca0ff")
    svg.addView("normalSizeView").viewBox('0 0 600 200')
    svg.addView('doubleSizeView').viewBox("0 0 300 100")
    svg.addA().xlinkHref('#normalSizeView')
      .addText().x(5).y(40).fontSize(20).addContent('normal size')
    svg.addA().xlinkHref('#doubleSizeView')
        .addText().x(5).y(60).fontSize(20).addContent('double size')
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))
  }
}
