package test.alipsa.groovy.svg

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.utils.NumberFormatter
import se.alipsa.groovy.svg.utils.PathBuilder
import se.alipsa.groovy.svg.utils.ViewBox

import static org.junit.jupiter.api.Assertions.*

/**
 * Integration tests for numeric precision control across the library.
 */
class NumericPrecisionIntegrationTest {

  @BeforeEach
  void setUp() {
    // Reset to default precision before each test
    NumberFormatter.resetPrecision()
  }

  @Test
  void testCircleWithDefaultPrecision() {
    def svg = new Svg(100, 100)
    def circle = svg.addCircle()
        .cx(12.123456789)
        .cy(50.0)
        .r(25.999999)

    String xml = circle.toXml()

    // Should use 3 decimal places (default)
    assert xml.contains('cx="12.123"')
    assert xml.contains('cy="50"')  // No .0 for whole numbers
    assert xml.contains('r="26"')   // Rounds to 26
  }

  void testRectWithDefaultPrecision() {
    def svg = new Svg(200, 200)
    def rect = svg.addRect()
        .x(10.123456)
        .y(20.987654)
        .width(100.5)
        .height(50.0)

    String xml = rect.toXml()

    assert xml.contains('x="10.123"')
    assert xml.contains('y="20.988"')
    assert xml.contains('width="100.5"')
    assert xml.contains('height="50"')
  }

  void testDocumentLevelPrecision() {
    def svg = new Svg(100, 100).setMaxPrecision(2)

    def circle = svg.addCircle()
        .cx(12.123456)
        .cy(50.0)
        .r(10.999)

    String xml = circle.toXml()

    // Should use 2 decimal places (document setting)
    assert xml.contains('cx="12.12"')
    assert xml.contains('cy="50"')
    assert xml.contains('r="11"')
  }

  void testGlobalPrecisionConfiguration() {
    // Set global precision to 1 decimal
    NumberFormatter.setDefaultPrecision(1)

    def svg = new Svg(100, 100)
    def circle = svg.addCircle()
        .cx(12.99)
        .cy(50.55)

    String xml = circle.toXml()

    assert xml.contains('cx="13"')
    assert xml.contains('cy="50.6"')
  }

  void testDocumentPrecisionOverridesGlobal() {
    // Set global to 1
    NumberFormatter.setDefaultPrecision(1)

    // Document overrides to 3
    def svg = new Svg(100, 100).setMaxPrecision(3)

    def circle = svg.addCircle()
        .cx(12.123456)

    String xml = circle.toXml()

    // Should use document precision (3), not global (1)
    assert xml.contains('cx="12.123"')
  }

  void testPathDataFormatting() {
    def svg = new Svg(100, 100)

    def path = PathBuilder.moveTo(10.123456, 20.789012)
        .lineTo(30.555555, 40.0)
        .curveTo(50.1, 60.2, 70.3, 80.4, 90.5, 100.6)
        .closePath()

    def pathElement = svg.addPath().d(path.toString())

    String xml = pathElement.toXml()

    // Path data should use formatted numbers
    assert xml.contains('M 10.123 20.789')
    assert xml.contains('L 30.556 40')
    assert xml.contains('C 50.1 60.2, 70.3 80.4, 90.5 100.6')
  }

  void testViewBoxFormatting() {
    def svg = new Svg(100, 100)

    def viewBox = ViewBox.of(0.5, 0.5, 100.123456, 100.987654)
    svg.viewBox(viewBox)

    String xml = svg.toXml()

    // ViewBox should use formatted numbers
    assert xml.contains('viewBox="0.5 0.5 100.123 100.988"')
  }

  void testViewBoxWithDocumentPrecision() {
    def svg = new Svg(100, 100).setMaxPrecision(2)

    // Note: ViewBox.toString() uses global precision, not document precision
    // because it's a utility class that doesn't know about the document
    def viewBox = ViewBox.of(0.123, 0.456, 100.789, 100.012)

    // ViewBox should still use global default (3 decimals)
    assert viewBox.toString() == "0.123 0.456 100.789 100.012"
  }

  void testFileSizeReduction() {
    // Create two identical SVGs with different precision
    def createSvg = { int precision ->
      NumberFormatter.setDefaultPrecision(precision)
      def svg = new Svg(1000, 1000)

      // Create 100 circles with many decimal places
      100.times { i ->
        svg.addCircle()
            .cx(i * 10.123456789)
            .cy(i * 10.987654321)
            .r(5.5555555)
      }
      return svg.toXml()
    }

    // Full precision (10 decimals)
    String fullXml = createSvg(10)

    // Default precision (3 decimals)
    String compactXml = createSvg(3)

    // Compact should be significantly smaller
    double reduction = 1.0 - ((double) compactXml.length() / fullXml.length())

    // Should have at least 20% reduction
    assert reduction > 0.20, "Expected >20% reduction, got ${(reduction * 100).round(1)}%"

    // For reference, print actual reduction
    println "File size reduction: ${(reduction * 100).round(1)}%"
    println "Full precision: ${fullXml.length()} bytes"
    println "Default precision: ${compactXml.length()} bytes"
  }

  void testZeroPrecisionForIntegers() {
    def svg = new Svg(100, 100).setMaxPrecision(0)

    def circle = svg.addCircle()
        .cx(12.9)
        .cy(50.4)
        .r(10.5)

    String xml = circle.toXml()

    // Should round to integers
    assert xml.contains('cx="13"')
    assert xml.contains('cy="50"')
    assert xml.contains('r="11"')  // Rounds up from 10.5
  }

  void testHighPrecisionWhenNeeded() {
    def svg = new Svg(100, 100).setMaxPrecision(6)

    def circle = svg.addCircle()
        .cx(12.123456789)

    String xml = circle.toXml()

    // Should preserve 6 decimals
    assert xml.contains('cx="12.123457"')  // Rounded at 6th decimal
  }

  void testTrailingZeroRemovalInAttributes() {
    def svg = new Svg(100, 100)

    def rect = svg.addRect()
        .x(10.100)
        .y(20.200)
        .width(30.300)

    String xml = rect.toXml()

    // Trailing zeros should be removed
    assert xml.contains('x="10.1"')
    assert xml.contains('y="20.2"')
    assert xml.contains('width="30.3"')
  }

  void testNegativeCoordinates() {
    def svg = new Svg(200, 200)

    def circle = svg.addCircle()
        .cx(-10.123456)
        .cy(-20.987654)

    String xml = circle.toXml()

    assert xml.contains('cx="-10.123"')
    assert xml.contains('cy="-20.988"')
  }

  void testPrecisionValidationInSvg() {
    def svg = new Svg(100, 100)

    // Valid precision values
    svg.setMaxPrecision(0)  // OK
    svg.setMaxPrecision(10)  // OK

    // Invalid precision values
    assertThrows(IllegalArgumentException) {
      svg.setMaxPrecision(-1)
    }

    assertThrows(IllegalArgumentException) {
      svg.setMaxPrecision(11)
    }
  }

  void testGetMaxPrecision() {
    def svg = new Svg(100, 100)

    // Default is null (uses global)
    assert svg.getMaxPrecision() == null

    // Set custom precision
    svg.setMaxPrecision(5)
    assert svg.getMaxPrecision() == 5

    // Change it
    svg.setMaxPrecision(2)
    assert svg.getMaxPrecision() == 2
  }

  void testPrecisionWithNestedElements() {
    def svg = new Svg(200, 200).setMaxPrecision(2)

    // Add a group with circles
    def group = svg.addG()
    def circle1 = group.addCircle().cx(10.123456).cy(20.456789)
    def circle2 = group.addCircle().cx(30.987654).cy(40.111111)

    // Both circles should inherit document precision
    assert circle1.toXml().contains('cx="10.12"')
    assert circle1.toXml().contains('cy="20.46"')
    assert circle2.toXml().contains('cx="30.99"')
    assert circle2.toXml().contains('cy="40.11"')
  }

  void testPrecisionWithDifferentShapes() {
    def svg = new Svg(200, 200).setMaxPrecision(2)

    // Test various shapes
    svg.addCircle().cx(10.123).cy(20.456).r(5.789)
    svg.addEllipse().cx(30.123).cy(40.456).rx(10.789).ry(15.234)
    svg.addRect().x(50.123).y(60.456).width(70.789).height(80.234)
    svg.addLine().x1(10.123).y1(20.456).x2(30.789).y2(40.234)

    String xml = svg.toXml()

    // All should use 2 decimal precision
    assert xml.contains('cx="10.12"')
    assert xml.contains('r="5.79"')
    assert xml.contains('rx="10.79"')
    assert xml.contains('width="70.79"')
    assert xml.contains('x1="10.12"')
  }
}
