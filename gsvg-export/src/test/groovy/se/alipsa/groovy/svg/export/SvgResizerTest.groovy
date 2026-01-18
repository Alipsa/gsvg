package se.alipsa.groovy.svg.export

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Svg

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNotSame

class SvgResizerTest {

  @Test
  void testResizeToWidthNumber() {
    Svg svg = new Svg(400, 200)

    Svg resized = SvgResizer.resizeToWidth(svg, 480)

    assertNotSame(svg, resized)
    assertEquals('480', resized.getWidth())
    assertEquals('240', resized.getHeight())
    assertEquals('400', svg.getWidth())
    assertEquals('200', svg.getHeight())
  }

  @Test
  void testResizeToWidthPercentUsesCurrentSize() {
    Svg svg = new Svg(400, 200)

    Svg resized = SvgResizer.resizeToWidth(svg, '150%')

    assertEquals('150%', resized.getWidth())
    assertEquals('150%', resized.getHeight())
  }

  @Test
  void testResizeKeepsAspectRatioByDefault() {
    Svg svg = new Svg(400, 200)

    Svg resized = SvgResizer.resize(svg, 300, 300)

    assertEquals('300', resized.getWidth())
    assertEquals('150', resized.getHeight())
  }

  @Test
  void testScalePreservesUnits() {
    Svg svg = new Svg('10cm', '5cm')

    Svg scaled = SvgResizer.scale(svg, 2)

    assertEquals('20cm', scaled.getWidth())
    assertEquals('10cm', scaled.getHeight())
  }
}
