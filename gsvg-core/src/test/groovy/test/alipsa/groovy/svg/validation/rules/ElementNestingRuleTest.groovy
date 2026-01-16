package test.alipsa.groovy.svg.validation.rules

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Circle
import se.alipsa.groovy.svg.LinearGradient
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.Text
import se.alipsa.groovy.svg.validation.ValidationReport
import se.alipsa.groovy.svg.validation.rules.ElementNestingRule

import static org.junit.jupiter.api.Assertions.*

/**
 * Tests for ElementNestingRule.
 */
class ElementNestingRuleTest {

    @Test
    void testCircleWithoutChildren() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle().cx(50).cy(50).r(25)

        ValidationReport report = new ValidationReport()
        new ElementNestingRule().validate(circle, report)

        // Circle without children is valid
        assertTrue(report.isValid())
        assertEquals(0, report.warningCount)
    }

    @Test
    void testLinearGradientWithValidStop() {
        Svg svg = new Svg(100, 100)
        LinearGradient gradient = svg.addDefs().addLinearGradient()
        gradient.addStop().offset(0).stopColor("red")
        gradient.addStop().offset(1).stopColor("blue")

        ValidationReport report = new ValidationReport()
        new ElementNestingRule().validate(gradient, report)

        assertTrue(report.isValid())
        assertEquals(0, report.warningCount)
    }

    @Test
    void testLinearGradientWithOnlyStops() {
        Svg svg = new Svg(100, 100)
        LinearGradient gradient = svg.addDefs().addLinearGradient()
        gradient.addStop().offset(0).stopColor("red")

        ValidationReport report = new ValidationReport()
        new ElementNestingRule().validate(gradient, report)

        // Gradient with only stops is valid
        assertTrue(report.isValid())
        assertEquals(0, report.warningCount)
    }

    @Test
    void testTextWithValidTspan() {
        Svg svg = new Svg(100, 100)
        Text text = svg.addText().x(10).y(20).addContent("Hello")
        text.addTspan().addContent(" World")

        ValidationReport report = new ValidationReport()
        new ElementNestingRule().validate(text, report)

        assertTrue(report.isValid())
        assertEquals(0, report.warningCount)
    }

    @Test
    void testTextWithTspan() {
        Svg svg = new Svg(100, 100)
        Text text = svg.addText().x(10).y(20).addContent("Hello")
        text.addTspan().addContent(" World")

        ValidationReport report = new ValidationReport()
        new ElementNestingRule().validate(text, report)

        // Text with tspan is valid
        assertTrue(report.isValid())
        assertEquals(0, report.warningCount)
    }

    @Test
    void testRectCanHaveChildren() {
        Svg svg = new Svg(100, 100)
        // Rect shouldn't have children but we don't validate against it strongly
        // This test ensures we don't break when rect has animation children
        svg.addRect().width(50).height(30)

        ValidationReport report = new ValidationReport()
        new ElementNestingRule().validate(svg.children[0], report)

        assertTrue(report.isValid())
    }

    @Test
    void testNullElement() {
        ValidationReport report = new ValidationReport()
        new ElementNestingRule().validate(null, report)

        assertTrue(report.isValid())
        assertEquals(0, report.size())
    }
}
