package test.alipsa.groovy.svg.utils

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.utils.NumberFormatter

import static org.junit.jupiter.api.Assertions.*

/**
 * Tests for {@link NumberFormatter}.
 */
class NumberFormatterTest {

  @BeforeEach
  void setUp() {
    // Reset to default before each test
    NumberFormatter.resetPrecision()
  }

  @Test
  void testIntegerFormatting() {
    assert NumberFormatter.format(50.0) == "50"
    assert NumberFormatter.format(50) == "50"
    assert NumberFormatter.format(100.00000) == "100"
    assert NumberFormatter.format(-25.0) == "-25"
    assert NumberFormatter.format(0.0) == "0"
  }

  @Test
  void testDecimalRounding() {
    // Default 3 decimals
    assert NumberFormatter.format(12.123456789) == "12.123"
    assert NumberFormatter.format(12.1) == "12.1"
    assert NumberFormatter.format(12.12) == "12.12"
    assert NumberFormatter.format(12.1234) == "12.123"
    assert NumberFormatter.format(12.1235) == "12.124"  // Rounds up
  }

  @Test
  void testTrailingZeroRemoval() {
    assert NumberFormatter.format(12.120) == "12.12"
    assert NumberFormatter.format(12.100) == "12.1"
    assert NumberFormatter.format(12.000) == "12"
    assert NumberFormatter.format(0.500) == "0.5"
    assert NumberFormatter.format(10.010) == "10.01"
  }

  @Test
  void testCustomPrecision() {
    assert NumberFormatter.format(12.123456, 5) == "12.12346"
    assert NumberFormatter.format(12.123456, 2) == "12.12"
    assert NumberFormatter.format(12.123456, 1) == "12.1"
    assert NumberFormatter.format(12.123456, 0) == "12"
    assert NumberFormatter.format(12.9, 0) == "13"  // Rounds to nearest integer
  }

  @Test
  void testGlobalPrecisionConfiguration() {
    // Set to 2 decimals
    NumberFormatter.setDefaultPrecision(2)
    assert NumberFormatter.getDefaultPrecision() == 2
    assert NumberFormatter.format(12.123456) == "12.12"
    assert NumberFormatter.format(12.999) == "13"

    // Set to 5 decimals
    NumberFormatter.setDefaultPrecision(5)
    assert NumberFormatter.getDefaultPrecision() == 5
    assert NumberFormatter.format(12.123456789) == "12.12346"

    // Set to 0 decimals
    NumberFormatter.setDefaultPrecision(0)
    assert NumberFormatter.format(12.123456) == "12"
    assert NumberFormatter.format(12.9) == "13"

    // Reset to default (3)
    NumberFormatter.resetPrecision()
    assert NumberFormatter.getDefaultPrecision() == 3
    assert NumberFormatter.format(12.123456) == "12.123"
  }

  @Test
  void testPrecisionValidation() {
    // Valid range: 0-10
    NumberFormatter.setDefaultPrecision(0)  // OK
    NumberFormatter.setDefaultPrecision(10)  // OK

    // Invalid: negative
    assertThrows(IllegalArgumentException) {
      NumberFormatter.setDefaultPrecision(-1)
    }

    // Invalid: too large
    assertThrows(IllegalArgumentException) {
      NumberFormatter.setDefaultPrecision(11)
    }
  }

  @Test
  void testNonNumericPassThrough() {
    assert NumberFormatter.format("red") == "red"
    assert NumberFormatter.format("10%") == "10%"
    assert NumberFormatter.format("url(#gradient)") == "url(#gradient)"
    assert NumberFormatter.format(true) == "true"
    assert NumberFormatter.format(false) == "false"
  }

  @Test
  void testNullHandling() {
    assert NumberFormatter.format(null) == null
    assert NumberFormatter.format(null, 5) == null
  }

  @Test
  void testSpecialFloatingPointValues() {
    assert NumberFormatter.format(Double.NaN) == "NaN"
    assert NumberFormatter.format(Double.POSITIVE_INFINITY) == "Infinity"
    assert NumberFormatter.format(Double.NEGATIVE_INFINITY) == "-Infinity"
  }

  @Test
  void testNegativeNumbers() {
    assert NumberFormatter.format(-12.123456) == "-12.123"
    assert NumberFormatter.format(-50.0) == "-50"
    assert NumberFormatter.format(-0.123) == "-0.123"
    assert NumberFormatter.format(-0.0001, 3) == "0"  // Rounds to 0
  }

  @Test
  void testVerySmallNumbers() {
    assert NumberFormatter.format(0.001) == "0.001"
    assert NumberFormatter.format(0.0001) == "0"  // Rounds to 0 with 3 decimals
    assert NumberFormatter.format(0.0001, 4) == "0.0001"
    assert NumberFormatter.format(0.00001, 5) == "0.00001"
  }

  @Test
  void testVeryLargeNumbers() {
    assert NumberFormatter.format(1000000.0) == "1000000"
    assert NumberFormatter.format(1000000.123) == "1000000.123"
    assert NumberFormatter.format(1000000.1234567) == "1000000.123"
  }

  @Test
  void testPrecisionOverrideDoesNotAffectGlobal() {
    // Set global to 3
    NumberFormatter.setDefaultPrecision(3)

    // Use custom precision
    assert NumberFormatter.format(12.123456, 5) == "12.12346"

    // Verify global unchanged
    assert NumberFormatter.getDefaultPrecision() == 3
    assert NumberFormatter.format(12.123456) == "12.123"
  }

  @Test
  void testEdgeCases() {
    // Very close to integer
    assert NumberFormatter.format(12.0000001) == "12"

    // Just over integer
    assert NumberFormatter.format(12.0001) == "12"

    // Exactly halfway (should round up)
    assert NumberFormatter.format(12.1235) == "12.124"
    assert NumberFormatter.format(12.1225, 2) == "12.12"  // Note: might vary by rounding mode
  }

  @Test
  void testDifferentNumberTypes() {
    // Integer
    assert NumberFormatter.format((int) 50) == "50"

    // Long
    assert NumberFormatter.format((long) 1000000) == "1000000"

    // Float
    assert NumberFormatter.format((float) 12.5) == "12.5"

    // Double
    assert NumberFormatter.format((double) 12.123) == "12.123"

    // BigDecimal
    assert NumberFormatter.format(new BigDecimal("12.123456")) == "12.123"
  }

  @Test
  void testThreadSafety() {
    // Test that thread-local precision works
    NumberFormatter.setDefaultPrecision(2)
    assert NumberFormatter.getDefaultPrecision() == 2

    Thread thread = new Thread({
      // Thread should inherit default
      assert NumberFormatter.getDefaultPrecision() == 3  // Initial default

      // Change in thread shouldn't affect main
      NumberFormatter.setDefaultPrecision(5)
      assert NumberFormatter.getDefaultPrecision() == 5
    })
    thread.start()
    thread.join()

    // Main thread should be unaffected
    assert NumberFormatter.getDefaultPrecision() == 2
  }
}
