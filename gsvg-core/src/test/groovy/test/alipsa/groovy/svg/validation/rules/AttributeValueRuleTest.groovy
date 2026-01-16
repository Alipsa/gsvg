package test.alipsa.groovy.svg.validation.rules

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Circle
import se.alipsa.groovy.svg.Rect
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.validation.ValidationReport
import se.alipsa.groovy.svg.validation.rules.AttributeValueRule

import static org.junit.jupiter.api.Assertions.*

/**
 * Tests for AttributeValueRule.
 */
class AttributeValueRuleTest {

    @Test
    void testValidPositiveWidth() {
        Svg svg = new Svg(100, 100)
        Rect rect = svg.addRect().width(50).height(30)

        ValidationReport report = new ValidationReport()
        new AttributeValueRule().validate(rect, report)

        assertTrue(report.isValid())
        assertEquals(0, report.errorCount)
    }

    @Test
    void testNegativeWidth() {
        Svg svg = new Svg(100, 100)
        Rect rect = svg.addRect().width("-10").height(30)

        ValidationReport report = new ValidationReport()
        new AttributeValueRule().validate(rect, report)

        assertFalse(report.isValid())
        assertEquals(1, report.errorCount)
        assertTrue(report.errors[0].message.contains("width"))
        assertTrue(report.errors[0].message.contains("non-negative"))
    }

    @Test
    void testInvalidNumericWidth() {
        Svg svg = new Svg(100, 100)
        Rect rect = svg.addRect().addAttribute("width", "abc")

        ValidationReport report = new ValidationReport()
        new AttributeValueRule().validate(rect, report)

        assertFalse(report.isValid())
        assertEquals(1, report.errorCount)
        assertTrue(report.errors[0].message.contains("valid number"))
    }

    @Test
    void testValidOpacity() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle().cx(50).cy(50).r(25).opacity(0.5)

        ValidationReport report = new ValidationReport()
        new AttributeValueRule().validate(circle, report)

        assertTrue(report.isValid())
        assertEquals(0, report.errorCount)
    }

    @Test
    void testOpacityTooHigh() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle().cx(50).cy(50).r(25).opacity("1.5")

        ValidationReport report = new ValidationReport()
        new AttributeValueRule().validate(circle, report)

        assertFalse(report.isValid())
        assertEquals(1, report.errorCount)
        assertTrue(report.errors[0].message.contains("opacity"))
        assertTrue(report.errors[0].message.contains("between 0 and 1"))
    }

    @Test
    void testOpacityTooLow() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle().cx(50).cy(50).r(25).opacity("-0.1")

        ValidationReport report = new ValidationReport()
        new AttributeValueRule().validate(circle, report)

        assertFalse(report.isValid())
        assertEquals(1, report.errorCount)
        assertTrue(report.errors[0].message.contains("opacity"))
    }

    @Test
    void testInvalidOpacity() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle().cx(50).cy(50).r(25).addAttribute("opacity", "invalid")

        ValidationReport report = new ValidationReport()
        new AttributeValueRule().validate(circle, report)

        assertFalse(report.isValid())
        assertEquals(1, report.errorCount)
        assertTrue(report.errors[0].message.contains("valid number"))
    }

    @Test
    void testValidFillOpacity() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle().cx(50).cy(50).r(25).fillOpacity(0.8)

        ValidationReport report = new ValidationReport()
        new AttributeValueRule().validate(circle, report)

        assertTrue(report.isValid())
        assertEquals(0, report.errorCount)
    }

    @Test
    void testValidStrokeOpacity() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle().cx(50).cy(50).r(25).strokeOpacity(0.3)

        ValidationReport report = new ValidationReport()
        new AttributeValueRule().validate(circle, report)

        assertTrue(report.isValid())
        assertEquals(0, report.errorCount)
    }

    @Test
    void testWidthWithUnits() {
        Svg svg = new Svg(100, 100)
        Rect rect = svg.addRect().addAttribute("width", "50px").height("30em")

        ValidationReport report = new ValidationReport()
        new AttributeValueRule().validate(rect, report)

        assertTrue(report.isValid())
        assertEquals(0, report.errorCount)
    }

    @Test
    void testNegativeRadius() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle().cx(50).cy(50).addAttribute("r", "-10")

        ValidationReport report = new ValidationReport()
        new AttributeValueRule().validate(circle, report)

        assertFalse(report.isValid())
        assertEquals(1, report.errorCount)
        assertTrue(report.errors[0].message.contains("r"))
    }

    @Test
    void testMultipleErrors() {
        Svg svg = new Svg(100, 100)
        Rect rect = svg.addRect()
            .addAttribute("width", "-10")
            .addAttribute("height", "-20")
            .addAttribute("opacity", "1.5")

        ValidationReport report = new ValidationReport()
        new AttributeValueRule().validate(rect, report)

        assertFalse(report.isValid())
        assertEquals(3, report.errorCount)
    }

    @Test
    void testNullElement() {
        ValidationReport report = new ValidationReport()
        new AttributeValueRule().validate(null, report)

        assertTrue(report.isValid())
        assertEquals(0, report.size())
    }
}
