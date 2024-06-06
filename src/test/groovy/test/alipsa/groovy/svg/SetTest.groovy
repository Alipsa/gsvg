package test.alipsa.groovy.svg

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.SvgReader
import se.alipsa.groovy.svg.SvgWriter

import static org.junit.jupiter.api.Assertions.assertEquals

class SetTest {

  @Test
  void testSet() {
    String svgContent = '''
      <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 10 10">
        <style>rect { cursor: pointer; } .round { rx: 5px; fill: green; }</style>      
        <rect id="me" width="10" height="10">
          <set attributeName="class" to="round" begin="me.click" dur="2s"/>
        </rect>
      </svg>
      '''.stripIndent()
    Svg svg = SvgReader.parse(svgContent)
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))

    svg = new Svg().viewBox("0 0 10 10")
    svg.addStyle().addContent('rect { cursor: pointer; } .round { rx: 5px; fill: green; }')
    svg.addRect('me').width(10).height(10)
      .addSet().attributeName('class').to('round').begin('me.click').dur('2s')
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))
  }
}
