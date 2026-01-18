package test.alipsa.groovy.svg

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Rect
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.Text
import se.alipsa.groovy.svg.io.SvgReader
import se.alipsa.groovy.svg.io.SvgWriter
import se.alipsa.groovy.svg.utils.SvgMerger

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertTrue

class V09CompatibilityTest {

  @Test
  void legacyCreationAndParsingRoundTrip() {
    Svg svg = new Svg(200, 120)
    Rect rect = svg.addRect(180, 100)
        .x(10).y(10)
        .rx(12).ry(12)
        .fill('#1976d2')
    rect.opacity = 0.9
    rect.addAttributes(stroke: '#0d47a1', strokeWidth: 3)

    Text text = svg.addText('Hello SVG')
        .x(30).y(70)
        .fill('white')
        .fontSize(24)

    String xml = SvgWriter.toXmlPretty(svg)
    assertTrue(xml.contains('<rect'))
    assertTrue(xml.contains('opacity="0.9"'))
    assertTrue(xml.contains('Hello SVG'))

    Svg parsed = SvgReader.parse(xml)
    Rect parsedRect = parsed[Rect][0] as Rect
    Text parsedText = parsed[Text][0] as Text

    assertEquals('0.9', parsedRect.opacity)
    assertEquals('#1976d2', parsedRect.fill)
    assertEquals('Hello SVG', parsedText.content)
  }

  @Test
  void legacyMergeHelpersRemainAvailable() {
    Svg left = new Svg(50, 40)
    left.addRect(50, 40).fill('red')

    Svg right = new Svg(30, 40)
    right.addRect(30, 40).fill('blue')

    Svg merged = SvgMerger.mergeHorizontally(left, right)

    assertEquals('80', merged.width)
    assertEquals('40', merged.height)
    assertEquals(2, merged.children.size())
  }
}
