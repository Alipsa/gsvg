package test.alipsa.groovy.svg

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Circle
import se.alipsa.groovy.svg.Rect
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.utils.Color

import static org.junit.jupiter.api.Assertions.*

/**
 * Tests for Color API integration with SVG elements.
 */
class ColorApiTest {

  @Test
  void testCircleFillWithColor() {
    Svg svg = new Svg(200, 200)
    Circle circle = svg.addCircle().cx(100).cy(100).r(50)
      .fill(Color.rgb(255, 0, 0))

    assertEquals('#ff0000', circle.getFill())
  }

  @Test
  void testCircleStrokeWithColor() {
    Svg svg = new Svg(200, 200)
    Circle circle = svg.addCircle().cx(100).cy(100).r(50)
      .stroke(Color.hex('#00ff00'))

    assertEquals('#00ff00', circle.getStroke())
  }

  @Test
  void testRectFillWithColorAlpha() {
    Svg svg = new Svg(200, 200)
    Rect rect = svg.addRect(100, 100)
      .fill(Color.rgba(255, 0, 0, 0.5))

    assertEquals('rgba(255, 0, 0, 0.5)', rect.getFill())
  }

  @Test
  void testRectStrokeWithNamedColor() {
    Svg svg = new Svg(200, 200)
    Rect rect = svg.addRect(100, 100)
      .stroke(Color.named('crimson'))

    assertEquals('#dc143c', rect.getStroke())
  }

  @Test
  void testCircleStrokeWithHslColor() {
    Svg svg = new Svg(200, 200)
    Circle circle = svg.addCircle().cx(100).cy(100).r(50)
      .stroke(Color.hsl(120, 100, 50))

    // HSL(120, 100, 50) = pure green = #00ff00
    assertEquals('#00ff00', circle.getStroke())
  }

  @Test
  void testChainedColorMethods() {
    Svg svg = new Svg(200, 200)
    Circle circle = svg.addCircle().cx(100).cy(100).r(50)
      .fill(Color.rgb(255, 0, 0))
      .stroke(Color.rgb(0, 0, 255))
      .strokeWidth(2)

    assertEquals('#ff0000', circle.getFill())
    assertEquals('#0000ff', circle.getStroke())
    assertEquals('2', circle.getStrokeWidth())
  }

  @Test
  void testColorManipulation() {
    Svg svg = new Svg(200, 200)
    def baseColor = Color.rgb(255, 0, 0)
    def darkerColor = baseColor.darken(0.2)

    Circle circle = svg.addCircle().cx(100).cy(100).r(50)
      .fill(darkerColor)

    // Darkened red should still be a valid hex color
    assertTrue(circle.getFill().startsWith('#'))
  }

  @Test
  void testColorInterpolation() {
    Svg svg = new Svg(200, 200)
    def red = Color.rgb(255, 0, 0)
    def blue = Color.rgb(0, 0, 255)
    def purple = red.interpolate(blue, 0.5)

    Rect rect = svg.addRect(100, 100)
      .fill(purple)

    // Purple (50% between red and blue)
    assertEquals('#7f007f', rect.getFill())
  }

  @Test
  void testColorWithAlpha() {
    Svg svg = new Svg(200, 200)
    def color = Color.hex('#00ff00').withAlpha(0.5)

    Circle circle = svg.addCircle().cx(100).cy(100).r(50)
      .fill(color)

    assertEquals('rgba(0, 255, 0, 0.5)', circle.getFill())
  }

  @Test
  void testStringAndColorCompatibility() {
    Svg svg = new Svg(200, 200)

    // Both String and Color should work
    Circle circle1 = svg.addCircle().cx(50).cy(50).r(25).fill('red')
    Circle circle2 = svg.addCircle().cx(150).cy(50).r(25).fill(Color.named('red'))

    assertEquals('red', circle1.getFill())
    assertEquals('#ff0000', circle2.getFill())
  }
}
