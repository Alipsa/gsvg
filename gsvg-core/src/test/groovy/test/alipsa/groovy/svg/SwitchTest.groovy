package test.alipsa.groovy.svg

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.io.SvgReader
import se.alipsa.groovy.svg.io.SvgWriter
import se.alipsa.groovy.svg.Switch

import static org.junit.jupiter.api.Assertions.*

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

  @Test
  void testSwitchSystemLanguage() {
    Svg svg = new Svg()
    Switch sw = svg.addSwitch()
            .systemLanguage('en')

    assertEquals('en', sw.systemLanguage)
    assertEquals('en', sw.getSystemLanguage())
    assertTrue(sw.toXml().contains('systemLanguage="en"'))
  }

  @Test
  void testSwitchSystemLanguageMultiple() {
    Svg svg = new Svg()
    Switch sw = svg.addSwitch()
            .systemLanguage('en,de,fr')

    assertEquals('en,de,fr', sw.systemLanguage)
  }

  @Test
  void testSwitchRequiredFeatures() {
    Svg svg = new Svg()
    Switch sw = svg.addSwitch()
            .requiredFeatures('http://www.w3.org/TR/SVG11/feature#Shape')

    assertEquals('http://www.w3.org/TR/SVG11/feature#Shape', sw.requiredFeatures)
    assertEquals('http://www.w3.org/TR/SVG11/feature#Shape', sw.getRequiredFeatures())
    assertTrue(sw.toXml().contains('requiredFeatures'))
  }

  @Test
  void testSwitchRequiredExtensions() {
    Svg svg = new Svg()
    Switch sw = svg.addSwitch()
            .requiredExtensions('http://example.org/SVGExtensions/EmbeddedXHTML')

    assertEquals('http://example.org/SVGExtensions/EmbeddedXHTML', sw.requiredExtensions)
    assertEquals('http://example.org/SVGExtensions/EmbeddedXHTML', sw.getRequiredExtensions())
    assertTrue(sw.toXml().contains('requiredExtensions'))
  }

  @Test
  void testSwitchCompleteAttributes() {
    Svg svg = new Svg()
    Switch sw = svg.addSwitch()
            .systemLanguage('en')
            .requiredFeatures('http://www.w3.org/TR/SVG11/feature#BasicText')
            .requiredExtensions('http://example.org/extensions')

    assertEquals('en', sw.systemLanguage)
    assertEquals('http://www.w3.org/TR/SVG11/feature#BasicText', sw.requiredFeatures)
    assertEquals('http://example.org/extensions', sw.requiredExtensions)
  }

  @Test
  void testSwitchMethodChaining() {
    Svg svg = new Svg()
    Switch sw = svg.addSwitch()

    Switch result = sw
            .systemLanguage('de')
            .requiredFeatures('feature1')
            .requiredExtensions('ext1')

    assertSame(sw, result)
  }

  @Test
  void testSwitchWithConditionalContent() {
    // Real-world scenario: Conditional rendering based on language
    Svg svg = new Svg(200, 100)
    Switch sw = svg.addSwitch()

    sw.addText('Guten Tag!')
            .x(10).y(50)
            .addAttribute('systemLanguage', 'de')

    sw.addText('Bonjour!')
            .x(10).y(50)
            .addAttribute('systemLanguage', 'fr')

    sw.addText('Hello!')
            .x(10).y(50)
            .addAttribute('systemLanguage', 'en')

    sw.addText('Hi!')  // Fallback
            .x(10).y(50)

    assertEquals(4, sw.children.size())
    assertTrue(sw.toXml().contains('<switch'))
  }

  @Test
  void testSwitchWithShapes() {
    // Real-world scenario: Conditionally render different shapes
    Svg svg = new Svg()
    Switch sw = svg.addSwitch()
            .requiredFeatures('http://www.w3.org/TR/SVG11/feature#BasicGraphicsAttribute')

    sw.addCircle()
            .cx(50).cy(50).r(40)
            .fill('red')

    sw.addRect()  // Fallback if circles not supported
            .x(10).y(10).width(80).height(80)
            .fill('blue')

    assertEquals(2, sw.children.size())
  }
}
