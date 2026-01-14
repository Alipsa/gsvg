package test.alipsa.groovy.svg.validation.rules

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Circle
import se.alipsa.groovy.svg.Ellipse
import se.alipsa.groovy.svg.Line
import se.alipsa.groovy.svg.Rect
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.Use
import se.alipsa.groovy.svg.validation.ValidationReport
import se.alipsa.groovy.svg.validation.rules.RequiredAttributeRule

import static org.junit.jupiter.api.Assertions.*

/**
 * Tests for RequiredAttributeRule.
 */
class RequiredAttributeRuleTest {

    @Test
    void testCircleWithAllRequiredAttributes() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle().cx(50).cy(50).r(25)

        ValidationReport report = new ValidationReport()
        new RequiredAttributeRule().validate(circle, report)

        assertTrue(report.isValid())
        assertEquals(0, report.errorCount)
    }

    @Test
    void testCircleMissingCx() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle().cy(50).r(25)

        ValidationReport report = new ValidationReport()
        new RequiredAttributeRule().validate(circle, report)

        assertFalse(report.isValid())
        assertEquals(1, report.errorCount)
        assertTrue(report.errors[0].message.contains("cx"))
    }

    @Test
    void testCircleMissingMultipleAttributes() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle()

        ValidationReport report = new ValidationReport()
        new RequiredAttributeRule().validate(circle, report)

        assertFalse(report.isValid())
        assertEquals(3, report.errorCount)  // Missing cx, cy, r
    }

    @Test
    void testEllipseWithAllRequiredAttributes() {
        Svg svg = new Svg(100, 100)
        Ellipse ellipse = svg.addEllipse().cx(50).cy(50)
        ellipse.addAttribute("rx", 30)
        ellipse.addAttribute("ry", 20)

        ValidationReport report = new ValidationReport()
        new RequiredAttributeRule().validate(ellipse, report)

        assertTrue(report.isValid())
        assertEquals(0, report.errorCount)
    }

    @Test
    void testEllipseMissingRx() {
        Svg svg = new Svg(100, 100)
        Ellipse ellipse = svg.addEllipse().cx(50).cy(50)
        ellipse.addAttribute("ry", 20)

        ValidationReport report = new ValidationReport()
        new RequiredAttributeRule().validate(ellipse, report)

        assertFalse(report.isValid())
        assertEquals(1, report.errorCount)
        assertTrue(report.errors[0].message.contains("rx"))
    }

    @Test
    void testLineWithAllRequiredAttributes() {
        Svg svg = new Svg(100, 100)
        Line line = svg.addLine().x1(0).y1(0).x2(100).y2(100)

        ValidationReport report = new ValidationReport()
        new RequiredAttributeRule().validate(line, report)

        assertTrue(report.isValid())
        assertEquals(0, report.errorCount)
    }

    @Test
    void testLineMissingX2() {
        Svg svg = new Svg(100, 100)
        Line line = svg.addLine().x1(0).y1(0).y2(100)

        ValidationReport report = new ValidationReport()
        new RequiredAttributeRule().validate(line, report)

        assertFalse(report.isValid())
        assertEquals(1, report.errorCount)
        assertTrue(report.errors[0].message.contains("x2"))
    }

    @Test
    void testUseWithHref() {
        Svg svg = new Svg(100, 100)
        Use use = svg.addUse()
        use.addAttribute("href", "#target")

        ValidationReport report = new ValidationReport()
        new RequiredAttributeRule().validate(use, report)

        assertTrue(report.isValid())
        assertEquals(0, report.errorCount)
    }

    @Test
    void testUseMissingHref() {
        Svg svg = new Svg(100, 100)
        Use use = svg.addUse()

        ValidationReport report = new ValidationReport()
        new RequiredAttributeRule().validate(use, report)

        assertFalse(report.isValid())
        assertEquals(1, report.errorCount)
        assertTrue(report.errors[0].message.contains("href"))
    }

    @Test
    void testRectWithoutWidthHeight() {
        Svg svg = new Svg(100, 100)
        Rect rect = svg.addRect().x(10).y(10)

        ValidationReport report = new ValidationReport()
        new RequiredAttributeRule().validate(rect, report)

        // Rect attributes are recommended, not required - should be warnings
        assertTrue(report.isValid())
        assertEquals(2, report.warningCount)  // Missing width and height
    }

    @Test
    void testRectWithWidthHeight() {
        Svg svg = new Svg(100, 100)
        Rect rect = svg.addRect().x(10).y(10).width(50).height(30)

        ValidationReport report = new ValidationReport()
        new RequiredAttributeRule().validate(rect, report)

        assertTrue(report.isValid())
        assertEquals(0, report.warningCount)
    }

    @Test
    void testNullElement() {
        ValidationReport report = new ValidationReport()
        new RequiredAttributeRule().validate(null, report)

        assertTrue(report.isValid())
        assertEquals(0, report.size())
    }
}
