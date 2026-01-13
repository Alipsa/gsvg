package test.alipsa.groovy.svg.utils

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.utils.Color

import static org.junit.jupiter.api.Assertions.*

class ColorTest {

  @Test
  void testRgbCreation() {
    def red = Color.rgb(255, 0, 0)
    assertEquals(255, red.red)
    assertEquals(0, red.green)
    assertEquals(0, red.blue)
    assertEquals(1.0, red.alpha, 0.001)
  }

  @Test
  void testRgbaCreation() {
    def semiTransparentRed = Color.rgba(255, 0, 0, 0.5)
    assertEquals(255, semiTransparentRed.red)
    assertEquals(0, semiTransparentRed.green)
    assertEquals(0, semiTransparentRed.blue)
    assertEquals(0.5, semiTransparentRed.alpha, 0.001)
  }

  @Test
  void testHexCreation3Digit() {
    def red = Color.hex('#f00')
    assertEquals(255, red.red)
    assertEquals(0, red.green)
    assertEquals(0, red.blue)
  }

  @Test
  void testHexCreation6Digit() {
    def blue = Color.hex('#0000ff')
    assertEquals(0, blue.red)
    assertEquals(0, blue.green)
    assertEquals(255, blue.blue)
  }

  @Test
  void testHexCreation8Digit() {
    def semiBlue = Color.hex('#0000ff80')
    assertEquals(0, semiBlue.red)
    assertEquals(0, semiBlue.green)
    assertEquals(255, semiBlue.blue)
    assertEquals(0.502, semiBlue.alpha, 0.01)  // 128/255 ≈ 0.502
  }

  @Test
  void testHexCreationWithoutHash() {
    def green = Color.hex('00ff00')
    assertEquals(0, green.red)
    assertEquals(255, green.green)
    assertEquals(0, green.blue)
  }

  @Test
  void testHslCreation() {
    // Red: hue 0, full saturation, 50% lightness
    def red = Color.hsl(0, 100, 50)
    assertEquals(255, red.red)
    assertEquals(0, red.green)
    assertEquals(0, red.blue)

    // Green: hue 120
    def green = Color.hsl(120, 100, 50)
    assertEquals(0, green.red)
    assertEquals(255, green.green)
    assertEquals(0, green.blue)

    // Blue: hue 240
    def blue = Color.hsl(240, 100, 50)
    assertEquals(0, blue.red)
    assertEquals(0, blue.green)
    assertEquals(255, blue.blue)
  }

  @Test
  void testHslaCreation() {
    def semiRed = Color.hsla(0, 100, 50, 0.5)
    assertEquals(255, semiRed.red)
    assertEquals(0, semiRed.green)
    assertEquals(0, semiRed.blue)
    assertEquals(0.5, semiRed.alpha, 0.001)
  }

  @Test
  void testNamedColorCreation() {
    def red = Color.named('red')
    assertEquals(255, red.red)
    assertEquals(0, red.green)
    assertEquals(0, red.blue)

    def blue = Color.named('blue')
    assertEquals(0, blue.red)
    assertEquals(0, blue.green)
    assertEquals(255, blue.blue)
  }

  @Test
  void testNamedColorCaseInsensitive() {
    def red1 = Color.named('RED')
    def red2 = Color.named('Red')
    def red3 = Color.named('red')

    assertEquals(red1.red, red2.red)
    assertEquals(red1.red, red3.red)
  }

  @Test
  void testInvalidNamedColor() {
    assertThrows(IllegalArgumentException) {
      Color.named('notacolor')
    }
  }

  @Test
  void testParseHex() {
    def color = Color.parse('#ff0000')
    assertEquals(255, color.red)
    assertEquals(0, color.green)
    assertEquals(0, color.blue)
  }

  @Test
  void testParseRgb() {
    def color = Color.parse('rgb(255, 128, 0)')
    assertEquals(255, color.red)
    assertEquals(128, color.green)
    assertEquals(0, color.blue)
  }

  @Test
  void testParseRgba() {
    def color = Color.parse('rgba(255, 128, 0, 0.5)')
    assertEquals(255, color.red)
    assertEquals(128, color.green)
    assertEquals(0, color.blue)
    assertEquals(0.5, color.alpha, 0.001)
  }

  @Test
  void testParseHsl() {
    def color = Color.parse('hsl(0, 100, 50)')
    assertEquals(255, color.red)
    assertEquals(0, color.green)
    assertEquals(0, color.blue)
  }

  @Test
  void testParseHsla() {
    def color = Color.parse('hsla(120, 100, 50, 0.7)')
    assertEquals(0, color.red)
    assertEquals(255, color.green)
    assertEquals(0, color.blue)
    assertEquals(0.7, color.alpha, 0.001)
  }

  @Test
  void testParseNamed() {
    def color = Color.parse('red')
    assertEquals(255, color.red)
    assertEquals(0, color.green)
    assertEquals(0, color.blue)
  }

  @Test
  void testWithAlpha() {
    def red = Color.rgb(255, 0, 0)
    def semiRed = red.withAlpha(0.5)

    assertEquals(255, semiRed.red)
    assertEquals(0, semiRed.green)
    assertEquals(0, semiRed.blue)
    assertEquals(0.5, semiRed.alpha, 0.001)

    // Original should be unchanged
    assertEquals(1.0, red.alpha, 0.001)
  }

  @Test
  void testDarken() {
    def red = Color.rgb(255, 0, 0)
    def darker = red.darken(0.5)

    assertEquals(127, darker.red, 1)  // Allow 1 unit tolerance for rounding
    assertEquals(0, darker.green)
    assertEquals(0, darker.blue)
  }

  @Test
  void testLighten() {
    def red = Color.rgb(255, 0, 0)
    def lighter = red.lighten(0.5)

    assertEquals(255, lighter.red)
    assertEquals(127, lighter.green, 1)
    assertEquals(127, lighter.blue, 1)
  }

  @Test
  void testInterpolate() {
    def red = Color.rgb(255, 0, 0)
    def blue = Color.rgb(0, 0, 255)

    // Midpoint should be purple
    def purple = red.interpolate(blue, 0.5)
    assertEquals(127, purple.red, 1)
    assertEquals(0, purple.green)
    assertEquals(127, purple.blue, 1)

    // At 0, should be red
    def atZero = red.interpolate(blue, 0.0)
    assertEquals(255, atZero.red)
    assertEquals(0, atZero.blue)

    // At 1, should be blue
    def atOne = red.interpolate(blue, 1.0)
    assertEquals(0, atOne.red)
    assertEquals(255, atOne.blue)
  }

  @Test
  void testToHex() {
    def red = Color.rgb(255, 0, 0)
    assertEquals('#ff0000', red.toHex())

    def semiRed = Color.rgba(255, 0, 0, 0.5)
    assertEquals('#ff0000', semiRed.toHex())
    assertEquals('#ff000080', semiRed.toHex(true))
  }

  @Test
  void testToRgb() {
    def red = Color.rgb(255, 0, 0)
    assertEquals('rgb(255, 0, 0)', red.toRgb())

    def semiRed = Color.rgba(255, 0, 0, 0.5)
    assertEquals('rgba(255, 0, 0, 0.5)', semiRed.toRgb())
  }

  @Test
  void testToHsl() {
    def red = Color.rgb(255, 0, 0)
    assertEquals('hsl(0, 100%, 50%)', red.toHsl())

    def semiRed = Color.rgba(255, 0, 0, 0.5)
    assertEquals('hsla(0, 100%, 50%, 0.5)', semiRed.toHsl())
  }

  @Test
  void testToString() {
    // Opaque color uses hex
    def red = Color.rgb(255, 0, 0)
    assertEquals('#ff0000', red.toString())

    // Transparent color uses rgba
    def semiRed = Color.rgba(255, 0, 0, 0.5)
    assertEquals('rgba(255, 0, 0, 0.5)', semiRed.toString())
  }

  @Test
  void testClampingRgb() {
    // Values over 255 should clamp to 255
    def color = Color.rgb(300, -10, 128)
    assertEquals(255, color.red)
    assertEquals(0, color.green)
    assertEquals(128, color.blue)
  }

  @Test
  void testClampingAlpha() {
    // Alpha over 1.0 should clamp to 1.0
    def color = Color.rgba(255, 0, 0, 1.5)
    assertEquals(1.0, color.alpha, 0.001)

    // Alpha under 0.0 should clamp to 0.0
    def color2 = Color.rgba(255, 0, 0, -0.5)
    assertEquals(0.0, color2.alpha, 0.001)
  }

  @Test
  void testEquals() {
    def red1 = Color.rgb(255, 0, 0)
    def red2 = Color.rgb(255, 0, 0)
    def blue = Color.rgb(0, 0, 255)

    assertEquals(red1, red2)
    assertNotEquals(red1, blue)
  }

  @Test
  void testHashCode() {
    def red1 = Color.rgb(255, 0, 0)
    def red2 = Color.rgb(255, 0, 0)

    assertEquals(red1.hashCode(), red2.hashCode())
  }

  @Test
  void testIntegrationWithSvg() {
    // Test that colors work as SVG attribute values
    def svg = new Svg(200, 200)
    def rect = svg.addRect(100, 100)
      .fill(Color.rgb(255, 0, 0).toString())
      .stroke(Color.hex('#00ff00').withAlpha(0.5).toString())

    assertEquals('#ff0000', rect.getFill())
    assertEquals('rgba(0, 255, 0, 0.5)', rect.getStroke())
  }
}
