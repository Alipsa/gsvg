package test.alipsa.groovy.svg.validation

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Circle
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.validation.ValidationEngine
import se.alipsa.groovy.svg.validation.ValidationIssue
import se.alipsa.groovy.svg.validation.ValidationReport
import se.alipsa.groovy.svg.validation.ValidationRule
import se.alipsa.groovy.svg.SvgElement

import static org.junit.jupiter.api.Assertions.*

/**
 * Tests for ValidationEngine.
 */
class ValidationEngineTest {

    @Test
    void testCreateDefault() {
        ValidationEngine engine = ValidationEngine.createDefault()

        Collection rules = engine.rules
        assertFalse(rules.isEmpty())

        // Should have 6 default rules
        assertTrue(rules.size() >= 6)

        // Verify specific default rules exist
        assertNotNull(engine.getRule("REQUIRED_ATTR"))
        assertNotNull(engine.getRule("ATTR_VALUE"))
        assertNotNull(engine.getRule("ELEMENT_NESTING"))
        assertNotNull(engine.getRule("VIEWBOX_RULE"))
        assertNotNull(engine.getRule("HREF_RULE"))
        assertNotNull(engine.getRule("DUPLICATE_ID"))
    }

    @Test
    void testAddRule() {
        ValidationEngine engine = new ValidationEngine()
        assertEquals(0, engine.rules.size())

        ValidationRule customRule = new ValidationRule() {
            String getRuleId() { "CUSTOM" }
            String getDescription() { "Custom rule" }
            void validate(SvgElement element, ValidationReport report) {}
        }

        engine.addRule(customRule)
        assertEquals(1, engine.rules.size())
        assertSame(customRule, engine.getRule("CUSTOM"))
    }

    @Test
    void testRemoveRule() {
        ValidationEngine engine = ValidationEngine.createDefault()
        int initialSize = engine.rules.size()

        engine.removeRule("VIEWBOX_RULE")
        assertEquals(initialSize - 1, engine.rules.size())
        assertNull(engine.getRule("VIEWBOX_RULE"))
    }

    @Test
    void testValidateElement() {
        ValidationEngine engine = ValidationEngine.createDefault()
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle().cx(50).cy(50).r(25)

        ValidationReport report = engine.validate(circle)

        assertTrue(report.isValid())
        assertEquals(0, report.errorCount)
    }

    @Test
    void testValidateElementWithErrors() {
        ValidationEngine engine = ValidationEngine.createDefault()
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle()  // Missing required attributes

        ValidationReport report = engine.validate(circle)

        assertFalse(report.isValid())
        assertTrue(report.errorCount > 0)
    }

    @Test
    void testValidateRecursively() {
        ValidationEngine engine = ValidationEngine.createDefault()
        Svg svg = new Svg(100, 100)
        svg.addCircle().cx(50).cy(50).r(25)  // Valid
        svg.addCircle()  // Invalid - missing attributes

        ValidationReport report = engine.validate(svg)

        assertFalse(report.isValid())
        assertTrue(report.errorCount > 0)
    }

    @Test
    void testCustomEngine() {
        ValidationEngine engine = new ValidationEngine()

        // Add only one specific rule
        ValidationRule strictRule = new ValidationRule() {
            String getRuleId() { "STRICT" }
            String getDescription() { "Strict validation" }

            void validate(SvgElement element, ValidationReport report) {
                if (element.id == null) {
                    report.addIssue(ValidationIssue.error(
                        "All elements must have an ID",
                        element.name,
                        element,
                        "STRICT"
                    ))
                }
            }
        }

        engine.addRule(strictRule)

        Svg svg = new Svg(100, 100)
        svg.addCircle().cx(50).cy(50).r(25)  // No ID

        ValidationReport report = engine.validate(svg)

        assertFalse(report.isValid())
        assertTrue(report.errors.any { it.message.contains("must have an ID") })
    }

    @Test
    void testRuleException() {
        ValidationEngine engine = new ValidationEngine()

        // Add a buggy rule that throws exception
        ValidationRule buggyRule = new ValidationRule() {
            String getRuleId() { "BUGGY" }
            String getDescription() { "Buggy rule" }

            void validate(SvgElement element, ValidationReport report) {
                throw new RuntimeException("Intentional error")
            }
        }

        engine.addRule(buggyRule)

        Svg svg = new Svg(100, 100)
        ValidationReport report = engine.validate(svg)

        // Should catch the exception and report it as an error
        assertFalse(report.isValid())
        assertTrue(report.errors.any { it.message.contains("threw exception") })
    }

    @Test
    void testValidateNull() {
        ValidationEngine engine = ValidationEngine.createDefault()
        ValidationReport report = engine.validate((SvgElement) null)

        assertTrue(report.isValid())
        assertEquals(0, report.size())
    }

    @Test
    void testAddNullRule() {
        ValidationEngine engine = new ValidationEngine()
        engine.addRule(null)

        assertEquals(0, engine.rules.size())
    }

    @Test
    void testGetRulesIsUnmodifiable() {
        ValidationEngine engine = ValidationEngine.createDefault()

        ValidationRule customRule = new ValidationRule() {
            String getRuleId() { "TEST" }
            String getDescription() { "Test" }
            void validate(SvgElement element, ValidationReport report) {}
        }

        assertThrows(UnsupportedOperationException.class, {
            engine.rules.add(customRule)
        })
    }
}
