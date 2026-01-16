package test.alipsa.groovy.svg

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Circle
import se.alipsa.groovy.svg.Rect
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.validation.ValidationEngine
import se.alipsa.groovy.svg.validation.ValidationReport

import static org.junit.jupiter.api.Assertions.*

/**
 * Integration tests for SVG validation through Svg and SvgElement validate() methods.
 */
class SvgValidationTest {

    @Test
    void testValidSvg() {
        Svg svg = new Svg(100, 100)
        svg.addCircle().cx(50).cy(50).r(25).fill("red")
        svg.addRect().x(10).y(10).width(50).height(30)

        ValidationReport report = svg.validate()

        assertTrue(report.isValid())
        assertEquals(0, report.errorCount)
    }

    @Test
    void testSvgWithErrors() {
        Svg svg = new Svg(100, 100)
        svg.addCircle()  // Missing required attributes

        ValidationReport report = svg.validate()

        assertFalse(report.isValid())
        assertTrue(report.errorCount > 0)
    }

    @Test
    void testSvgIsValid() {
        Svg svg = new Svg(100, 100)
        svg.addCircle().cx(50).cy(50).r(25)

        assertTrue(svg.isValid())
    }

    @Test
    void testSvgIsNotValid() {
        Svg svg = new Svg(100, 100)
        svg.addCircle()  // Missing required attributes

        assertFalse(svg.isValid())
    }

    @Test
    void testSvgWithCustomEngine() {
        ValidationEngine engine = ValidationEngine.createDefault()
        engine.removeRule("VIEWBOX_RULE")  // Don't check viewBox

        Svg svg = new Svg(100, 100)
        // No viewBox, but we disabled that rule
        svg.addCircle().cx(50).cy(50).r(25)

        ValidationReport report = svg.validate(engine)

        assertTrue(report.isValid())
        // Should not have viewBox info message
        assertEquals(0, report.infoCount)
    }

    @Test
    void testElementValidate() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle().cx(50).cy(50).r(25)

        ValidationReport report = circle.validate()

        assertTrue(report.isValid())
        assertEquals(0, report.errorCount)
    }

    @Test
    void testElementIsValid() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle().cx(50).cy(50).r(25)

        assertTrue(circle.isValid())
    }

    @Test
    void testElementValidateWithErrors() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle()  // Missing required attributes

        ValidationReport report = circle.validate()

        assertFalse(report.isValid())
        assertTrue(report.errorCount > 0)
    }

    @Test
    void testDuplicateIds() {
        Svg svg = new Svg(100, 100)
        svg.addCircle().cx(50).cy(50).r(25).id("shape1")
        svg.addRect().x(10).y(10).width(50).height(30).id("shape1")  // Duplicate ID

        ValidationReport report = svg.validate()

        assertFalse(report.isValid())
        assertTrue(report.errors.any { it.message.contains("Duplicate ID") })
    }

    @Test
    void testInvalidHrefReference() {
        Svg svg = new Svg(100, 100)
        svg.addUse().href("#nonexistent")  // References non-existent ID

        ValidationReport report = svg.validate()

        assertFalse(report.isValid())
        assertTrue(report.errors.any { it.message.contains("non-existent") })
    }

    @Test
    void testValidHrefReference() {
        Svg svg = new Svg(100, 100)
        svg.addDefs().addCircle().id("template").cx(50).cy(50).r(25)
        svg.addUse().href("#template")

        ValidationReport report = svg.validate()

        assertTrue(report.isValid())
    }

    @Test
    void testViewBoxMissing() {
        Svg svg = new Svg(100, 100)
        // No viewBox set

        ValidationReport report = svg.validate()

        // Should have info message about missing viewBox
        assertTrue(report.isValid())  // Info doesn't prevent valid
        assertTrue(report.infoCount > 0)
        assertTrue(report.info.any { it.message.contains("viewBox") })
    }

    @Test
    void testInvalidViewBox() {
        Svg svg = new Svg(100, 100)
        svg.addAttribute("viewBox", "0 0 -100 50")  // Negative width

        ValidationReport report = svg.validate()

        assertFalse(report.isValid())
        assertTrue(report.errors.any { it.message.contains("viewBox") && it.message.contains("positive") })
    }

    @Test
    void testInvalidAttributeValue() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle().cx(50).cy(50).r(25)
        circle.addAttribute("opacity", "2.0")  // Opacity must be 0-1

        ValidationReport report = svg.validate()

        assertFalse(report.isValid())
        assertTrue(report.errors.any { it.message.contains("opacity") && it.message.contains("between 0 and 1") })
    }

    @Test
    void testComplexScenario() {
        Svg svg = new Svg(800, 600)
        svg.addAttribute("viewBox", "0 0 800 600")

        // Add valid elements
        svg.addCircle().id("c1").cx(100).cy(100).r(50).fill("blue").opacity(0.8)
        svg.addRect().id("r1").x(200).y(200).width(100).height(80).fill("red")

        // Add gradient with stops
        def gradient = svg.addDefs().addLinearGradient().id("grad1")
        gradient.addStop().offset(0).stopColor("yellow")
        gradient.addStop().offset(1).stopColor("green")

        // Use the gradient
        svg.addRect().x(10).y(10).width(50).height(50).fill("url(#grad1)")

        ValidationReport report = svg.validate()

        assertTrue(report.isValid())
        assertEquals(0, report.errorCount)
    }

    @Test
    void testWarningsDoNotPreventValid() {
        Svg svg = new Svg(100, 100)
        Rect rect = svg.addRect()  // Missing recommended width/height

        ValidationReport report = svg.validate()

        // Should be valid despite warnings
        assertTrue(report.isValid())
        assertTrue(report.warningCount > 0)
    }
}
