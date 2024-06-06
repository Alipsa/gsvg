package test.alipsa.groovy.svg

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.SvgReader
import se.alipsa.groovy.svg.SvgWriter
import se.alipsa.groovy.svg.Switch

import static org.junit.jupiter.api.Assertions.assertEquals

class SwitchTest {

  @Test
  void testSwitch() {
    String svgContent = '''
      <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 -20 100 50">
        <switch>
          <text systemLanguage="de,nl">Hallo!</text>
          <text systemLanguage="en-us">Howdy!</text>
          <text systemLanguage="en-au">G'day!</text>
          <text systemLanguage="en">Hello!</text>
          <text systemLanguage="ja">こんにちは</text>
          <text>☺</text>
        </switch>
      </svg>
      '''.stripIndent()

    Svg svg = SvgReader.parse(svgContent)
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))

    svg = new Svg().viewBox("0 -20 100 50")
    Switch s = svg.addSwitch()
    s.addText('Hallo!').addAttribute('systemLanguage', "de,nl")
    s.addText('Howdy!').addAttribute('systemLanguage', "en-us")
    s.addText("G'day!").addAttribute('systemLanguage', "en-au")
    s.addText('Hello!').addAttribute('systemLanguage', "en")
    s.addText('こんにちは').addAttribute('systemLanguage', "ja")
    s.addText('☺')
    assertEquals(svgContent, SvgWriter.toXmlPretty(svg))
  }
}
