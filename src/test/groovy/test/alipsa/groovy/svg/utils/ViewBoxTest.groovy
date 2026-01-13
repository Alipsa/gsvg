package test.alipsa.groovy.svg.utils

import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.Symbol
import se.alipsa.groovy.svg.View
import se.alipsa.groovy.svg.utils.ViewBox
import static org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ViewBoxTest {

  @Test
  void testOfBasic() {
    ViewBox vb = ViewBox.of(0, 0, 100, 100)
    assertEquals(0, vb.minX)
    assertEquals(0, vb.minY)
    assertEquals(100, vb.width)
    assertEquals(100, vb.height)
  }

  @Test
  void testOfWithNegativeMinCoordinates() {
    ViewBox vb = ViewBox.of(-50, -50, 100, 100)
    assertEquals(-50, vb.minX)
    assertEquals(-50, vb.minY)
    assertEquals(100, vb.width)
    assertEquals(100, vb.height)
  }

  @Test
  void testOfWithInvalidWidth() {
    assertThrows(IllegalArgumentException.class) {
      ViewBox.of(0, 0, 0, 100)
    }
  }

  @Test
  void testOfWithInvalidHeight() {
    assertThrows(IllegalArgumentException.class) {
      ViewBox.of(0, 0, 100, -10)
    }
  }

  @Test
  void testParseSpaceSeparated() {
    ViewBox vb = ViewBox.parse('0 0 100 100')
    assertEquals(0, vb.minX)
    assertEquals(0, vb.minY)
    assertEquals(100, vb.width)
    assertEquals(100, vb.height)
  }

  @Test
  void testParseCommaSeparated() {
    ViewBox vb = ViewBox.parse('0,0,100,100')
    assertEquals(0, vb.minX)
    assertEquals(0, vb.minY)
    assertEquals(100, vb.width)
    assertEquals(100, vb.height)
  }

  @Test
  void testParseMixedSeparators() {
    ViewBox vb = ViewBox.parse('0, 0, 100 100')
    assertEquals(0, vb.minX)
    assertEquals(0, vb.minY)
    assertEquals(100, vb.width)
    assertEquals(100, vb.height)
  }

  @Test
  void testParseWithNegativeValues() {
    ViewBox vb = ViewBox.parse('-50 -50 200 200')
    assertEquals(-50, vb.minX)
    assertEquals(-50, vb.minY)
    assertEquals(200, vb.width)
    assertEquals(200, vb.height)
  }

  @Test
  void testParseWithDecimals() {
    ViewBox vb = ViewBox.parse('0.5 0.5 100.5 100.5')
    assertEquals(0.5, vb.minX, 0.001)
    assertEquals(0.5, vb.minY, 0.001)
    assertEquals(100.5, vb.width, 0.001)
    assertEquals(100.5, vb.height, 0.001)
  }

  @Test
  void testParseInvalidString() {
    assertThrows(IllegalArgumentException.class) {
      ViewBox.parse('invalid')
    }
  }

  @Test
  void testParseEmpty() {
    assertThrows(IllegalArgumentException.class) {
      ViewBox.parse('')
    }
  }

  @Test
  void testParseNull() {
    assertThrows(IllegalArgumentException.class) {
      ViewBox.parse(null)
    }
  }

  @Test
  void testParseTooFewValues() {
    assertThrows(IllegalArgumentException.class) {
      ViewBox.parse('0 0 100')
    }
  }

  @Test
  void testParseTooManyValues() {
    assertThrows(IllegalArgumentException.class) {
      ViewBox.parse('0 0 100 100 extra')
    }
  }

  @Test
  void testToString() {
    ViewBox vb = ViewBox.of(0, 0, 100, 100)
    assertEquals('0 0 100 100', vb.toString())
  }

  @Test
  void testToStringWithDecimals() {
    ViewBox vb = ViewBox.of(0.5, 0.5, 100.5, 100.5)
    assertEquals('0.5 0.5 100.5 100.5', vb.toString())
  }

  @Test
  void testToStringWithNegatives() {
    ViewBox vb = ViewBox.of(-50, -50, 200, 200)
    assertEquals('-50 -50 200 200', vb.toString())
  }

  @Test
  void testScaleUniform() {
    ViewBox vb = ViewBox.of(0, 0, 100, 100).scale(2)
    assertEquals(0, vb.minX)
    assertEquals(0, vb.minY)
    assertEquals(200, vb.width)
    assertEquals(200, vb.height)
  }

  @Test
  void testScaleNonUniform() {
    ViewBox vb = ViewBox.of(0, 0, 100, 100).scale(2, 3)
    assertEquals(0, vb.minX)
    assertEquals(0, vb.minY)
    assertEquals(200, vb.width)
    assertEquals(300, vb.height)
  }

  @Test
  void testScaleInvalidFactor() {
    assertThrows(IllegalArgumentException.class) {
      ViewBox.of(0, 0, 100, 100).scale(0)
    }
  }

  @Test
  void testTranslate() {
    ViewBox vb = ViewBox.of(0, 0, 100, 100).translate(10, 20)
    assertEquals(10, vb.minX)
    assertEquals(20, vb.minY)
    assertEquals(100, vb.width)
    assertEquals(100, vb.height)
  }

  @Test
  void testTranslateNegative() {
    ViewBox vb = ViewBox.of(50, 50, 100, 100).translate(-25, -25)
    assertEquals(25, vb.minX)
    assertEquals(25, vb.minY)
    assertEquals(100, vb.width)
    assertEquals(100, vb.height)
  }

  @Test
  void testExpandUniform() {
    ViewBox vb = ViewBox.of(10, 10, 80, 80).expand(10)
    assertEquals(0, vb.minX)
    assertEquals(0, vb.minY)
    assertEquals(100, vb.width)
    assertEquals(100, vb.height)
  }

  @Test
  void testExpandNonUniform() {
    ViewBox vb = ViewBox.of(10, 10, 80, 80).expand(5, 10, 15, 20)
    // top=5, right=10, bottom=15, left=20
    // minX = 10 - 20 = -10
    // minY = 10 - 5 = 5
    // width = 80 + 20 + 10 = 110
    // height = 80 + 5 + 15 = 100
    assertEquals(-10, vb.minX)
    assertEquals(5, vb.minY)
    assertEquals(110, vb.width)
    assertEquals(100, vb.height)
  }

  @Test
  void testExpandInvalidResult() {
    assertThrows(IllegalArgumentException.class) {
      ViewBox.of(0, 0, 10, 10).expand(-20)
    }
  }

  @Test
  void testCenterAt() {
    ViewBox vb = ViewBox.of(0, 0, 100, 100).centerAt(50, 50)
    assertEquals(0, vb.minX)
    assertEquals(0, vb.minY)
    assertEquals(100, vb.width)
    assertEquals(100, vb.height)
  }

  @Test
  void testCenterAtDifferentPoint() {
    ViewBox vb = ViewBox.of(0, 0, 100, 100).centerAt(100, 100)
    assertEquals(50, vb.minX)
    assertEquals(50, vb.minY)
    assertEquals(100, vb.width)
    assertEquals(100, vb.height)
  }

  @Test
  void testWithAspectRatio() {
    ViewBox vb = ViewBox.of(0, 0, 100, 100).withAspectRatio(2)
    // width = 100, aspect = 2 (width/height), so height = 50
    assertEquals(0, vb.minX)
    assertEquals(0, vb.minY)
    assertEquals(100, vb.width)
    assertEquals(50, vb.height)
  }

  @Test
  void testWithAspectRatioInvalid() {
    assertThrows(IllegalArgumentException.class) {
      ViewBox.of(0, 0, 100, 100).withAspectRatio(-1)
    }
  }

  @Test
  void testGetCenterX() {
    ViewBox vb = ViewBox.of(0, 0, 100, 100)
    assertEquals(50, vb.centerX)
  }

  @Test
  void testGetCenterY() {
    ViewBox vb = ViewBox.of(0, 0, 100, 100)
    assertEquals(50, vb.centerY)
  }

  @Test
  void testGetMaxX() {
    ViewBox vb = ViewBox.of(10, 10, 80, 80)
    assertEquals(90, vb.maxX)
  }

  @Test
  void testGetMaxY() {
    ViewBox vb = ViewBox.of(10, 10, 80, 80)
    assertEquals(90, vb.maxY)
  }

  @Test
  void testGetAspectRatio() {
    ViewBox vb = ViewBox.of(0, 0, 200, 100)
    assertEquals(2.0, vb.aspectRatio, 0.001)
  }

  @Test
  void testContainsPoint() {
    ViewBox vb = ViewBox.of(0, 0, 100, 100)
    assertTrue(vb.contains(50, 50))
    assertTrue(vb.contains(0, 0))
    assertTrue(vb.contains(100, 100))
    assertFalse(vb.contains(-1, 50))
    assertFalse(vb.contains(101, 50))
  }

  @Test
  void testContainsViewBox() {
    ViewBox outer = ViewBox.of(0, 0, 100, 100)
    ViewBox inner = ViewBox.of(10, 10, 80, 80)
    ViewBox overlapping = ViewBox.of(50, 50, 100, 100)

    assertTrue(outer.contains(inner))
    assertFalse(outer.contains(overlapping))
    assertFalse(inner.contains(outer))
  }

  @Test
  void testIntersects() {
    ViewBox vb1 = ViewBox.of(0, 0, 100, 100)
    ViewBox vb2 = ViewBox.of(50, 50, 100, 100)
    ViewBox vb3 = ViewBox.of(200, 200, 100, 100)

    assertTrue(vb1.intersects(vb2))
    assertTrue(vb2.intersects(vb1))
    assertFalse(vb1.intersects(vb3))
    assertFalse(vb3.intersects(vb1))
  }

  @Test
  void testToMap() {
    ViewBox vb = ViewBox.of(10, 20, 100, 200)
    Map<String, Double> map = vb.toMap()

    assertEquals(10.0, map.minX)
    assertEquals(20.0, map.minY)
    assertEquals(100.0, map.width)
    assertEquals(200.0, map.height)
  }

  @Test
  void testImmutability() {
    ViewBox original = ViewBox.of(0, 0, 100, 100)
    ViewBox scaled = original.scale(2)
    ViewBox translated = original.translate(10, 10)

    // Original should be unchanged
    assertEquals(0, original.minX)
    assertEquals(0, original.minY)
    assertEquals(100, original.width)
    assertEquals(100, original.height)

    // New instances should be different
    assertNotSame(original, scaled)
    assertNotSame(original, translated)
  }

  @Test
  void testIntegrationWithSvg() {
    Svg svg = new Svg()
    svg.viewBox(ViewBox.of(0, 0, 200, 200))

    assertEquals('0 0 200 200', svg.getViewBox())
  }

  @Test
  void testIntegrationWithSvgFourParams() {
    Svg svg = new Svg()
    svg.viewBox(0, 0, 300, 300)

    assertEquals('0 0 300 300', svg.getViewBox())
  }

  @Test
  void testIntegrationWithSvgGetObject() {
    Svg svg = new Svg()
    svg.viewBox('10 10 100 100')

    ViewBox vb = svg.getViewBoxObject()
    assertEquals(10, vb.minX)
    assertEquals(10, vb.minY)
    assertEquals(100, vb.width)
    assertEquals(100, vb.height)
  }

  @Test
  void testIntegrationWithSvgGetObjectNull() {
    Svg svg = new Svg()
    ViewBox vb = svg.getViewBoxObject()
    assertNull(vb)
  }

  @Test
  void testIntegrationWithSymbol() {
    Svg svg = new Svg()
    Symbol symbol = svg.addSymbol()
    symbol.viewBox(ViewBox.of(0, 0, 50, 50))

    assertEquals('0 0 50 50', symbol.getViewBox())
  }

  @Test
  void testIntegrationWithView() {
    Svg svg = new Svg()
    View view = svg.addView()
    view.viewBox(ViewBox.of(0, 0, 100, 100))

    assertEquals('0 0 100 100', view.getViewBox())
  }

  @Test
  void testChaining() {
    ViewBox vb = ViewBox.of(0, 0, 100, 100)
        .scale(2)
        .translate(10, 10)
        .expand(5)

    assertEquals(5, vb.minX)
    assertEquals(5, vb.minY)
    assertEquals(210, vb.width)
    assertEquals(210, vb.height)
  }

  @Test
  void testParseRoundTrip() {
    String original = '10 20 100 200'
    ViewBox vb = ViewBox.parse(original)
    String result = vb.toString()
    assertEquals(original, result)
  }

  @Test
  void testNumberTypes() {
    // Test with different number types
    ViewBox vb = ViewBox.of(10, 20.5, 100L, 200.0f)
    assertEquals(10, vb.minX, 0.001)
    assertEquals(20.5, vb.minY, 0.001)
    assertEquals(100, vb.width, 0.001)
    assertEquals(200, vb.height, 0.001)
  }

  @Test
  void testFitToContain() {
    ViewBox outer = ViewBox.of(0, 0, 200, 200)
    ViewBox inner = ViewBox.of(0, 0, 100, 100)

    ViewBox fitted = outer.fitToContain(inner, true)

    // Should preserve aspect ratio and center the fit
    assertTrue(fitted.width <= outer.width)
    assertTrue(fitted.height <= outer.height)
  }

  @Test
  void testComplexTransformations() {
    ViewBox vb = ViewBox.of(0, 0, 100, 100)
        .scale(2)              // 0 0 200 200
        .translate(50, 50)     // 50 50 200 200
        .expand(25)            // 25 25 250 250
        .centerAt(0, 0)        // -125 -125 250 250
        .scale(0.5)            // -125 -125 125 125

    assertEquals(-125, vb.minX)
    assertEquals(-125, vb.minY)
    assertEquals(125, vb.width)
    assertEquals(125, vb.height)
  }

  @Test
  void testEquals() {
    ViewBox vb1 = ViewBox.of(0, 0, 100, 100)
    ViewBox vb2 = ViewBox.of(0, 0, 100, 100)
    ViewBox vb3 = ViewBox.of(0, 0, 100, 200)

    assertEquals(vb1, vb2)
    assertNotEquals(vb1, vb3)
  }

  @Test
  void testHashCode() {
    ViewBox vb1 = ViewBox.of(0, 0, 100, 100)
    ViewBox vb2 = ViewBox.of(0, 0, 100, 100)

    assertEquals(vb1.hashCode(), vb2.hashCode())
  }
}
