package test.alipsa.groovy.svg.validation.rules

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.*
import se.alipsa.groovy.svg.validation.ValidationEngine
import se.alipsa.groovy.svg.validation.ValidationReport
import se.alipsa.groovy.svg.validation.ValidationIssue
import se.alipsa.groovy.svg.validation.rules.AccessibilityRule

import static org.junit.jupiter.api.Assertions.*

/**
 * Tests for AccessibilityRule validation logic.
 */
class AccessibilityRuleTest {

    private AccessibilityRule rule
    private ValidationEngine engine

    @BeforeEach
    void setUp() {
        rule = new AccessibilityRule()
        // Use an engine with ONLY the AccessibilityRule, not the default rules
        engine = new ValidationEngine()
        engine.addRule(rule)
    }

    @Test
    void testRuleMetadata() {
        assertEquals('ACCESSIBILITY_RULE', rule.getRuleId())
        assertNotNull(rule.getDescription())
        assertTrue(rule.getDescription().contains('accessibility'))
    }

    @Test
    void testRootSvgWithoutAccessibleName() {
        Svg svg = new Svg()
        svg.addCircle().cx(50).cy(50).r(25)

        ValidationReport report = engine.validate(svg)

        assertEquals(1, report.getWarnings().size())
        ValidationIssue issue = report.getWarnings().get(0)
        assertTrue(issue.getMessage().contains('Root <svg> should have an accessible name'))
        assertEquals('/svg', issue.elementPath)
        assertEquals('ACCESSIBILITY_RULE', issue.getRuleId())
    }

    @Test
    void testRootSvgWithRole() {
        Svg svg = new Svg()
        svg.role('img')
        svg.addCircle().cx(50).cy(50).r(25)

        ValidationReport report = engine.validate(svg)

        // Should have warning about role='img' needing a label
        assertEquals(1, report.getWarnings().size())
        assertTrue(report.getWarnings().get(0).getMessage().contains("role='img' should have an accessible label"))
    }

    @Test
    void testRootSvgWithRoleAndLabel() {
        Svg svg = new Svg()
        svg.role('img').ariaLabel('My accessible graphic')
        svg.addCircle().cx(50).cy(50).r(25)

        ValidationReport report = engine.validate(svg)

        assertEquals(0, report.getWarnings().size())
        assertEquals(0, report.getErrors().size())
        assertTrue(report.isValid())
    }

    @Test
    void testRootSvgWithTitle() {
        Svg svg = new Svg()
        svg.addTitle('My SVG Graphic')
        svg.addCircle().cx(50).cy(50).r(25)

        ValidationReport report = engine.validate(svg)

        assertEquals(0, report.getWarnings().size())
        assertEquals(0, report.getErrors().size())
        assertTrue(report.isValid())
    }

    @Test
    void testRootSvgWithAriaLabelledBy() {
        Svg svg = new Svg()
        svg.ariaLabelledBy('title1')
        svg.addTitle('Chart Title').id('title1')
        svg.addCircle().cx(50).cy(50).r(25)

        ValidationReport report = engine.validate(svg)

        assertEquals(0, report.getWarnings().size())
        assertEquals(0, report.getErrors().size())
        assertTrue(report.isValid())
    }

    @Test
    void testAriaLabelledByWithInvalidReference() {
        Svg svg = new Svg()
        svg.ariaLabelledBy('nonexistent')
        svg.addCircle().cx(50).cy(50).r(25)

        ValidationReport report = engine.validate(svg)

        assertEquals(1, report.getErrors().size())
        ValidationIssue error = report.getErrors().get(0)
        assertTrue(error.getMessage().contains('aria-labelledby'))
        assertTrue(error.getMessage().contains('nonexistent'))
        assertTrue(error.getMessage().contains('non-existent'))
    }

    @Test
    void testAriaDescribedByWithInvalidReference() {
        Svg svg = new Svg()
        svg.role('img').ariaLabel('Valid label')
        svg.ariaDescribedBy('missing-id')
        svg.addCircle().cx(50).cy(50).r(25)

        ValidationReport report = engine.validate(svg)

        assertEquals(1, report.getErrors().size())
        ValidationIssue error = report.getErrors().get(0)
        assertTrue(error.getMessage().contains('aria-describedby'))
        assertTrue(error.getMessage().contains('missing-id'))
    }

    @Test
    void testAriaLabelledByWithMultipleIds() {
        Svg svg = new Svg()
        svg.role('img')
        svg.ariaLabelledBy('title1 desc1')
        svg.addTitle('Title').id('title1')
        svg.addDesc('Description').id('desc1')

        ValidationReport report = engine.validate(svg)

        assertEquals(0, report.getErrors().size())
        assertTrue(report.isValid())
    }

    @Test
    void testAriaLabelledByWithSomeInvalidIds() {
        Svg svg = new Svg()
        svg.role('img')
        svg.ariaLabelledBy('title1 invalid desc1')
        svg.addTitle('Title').id('title1')
        svg.addDesc('Description').id('desc1')

        ValidationReport report = engine.validate(svg)

        assertEquals(1, report.getErrors().size())
        ValidationIssue error = report.getErrors().get(0)
        assertTrue(error.getMessage().contains('invalid'))
    }

    @Test
    void testInteractiveLinkWithoutLabel() {
        Svg svg = new Svg()
        svg.role('img').ariaLabel('Chart')

        A link = svg.addA()
        link.addCircle().cx(50).cy(50).r(25).fill('blue')

        ValidationReport report = engine.validate(svg)

        assertEquals(1, report.getWarnings().size())
        ValidationIssue warning = report.getWarnings().get(0)
        assertTrue(warning.getMessage().contains('Interactive element'))
        assertTrue(warning.getMessage().contains('<a>'))
        assertTrue(warning.getMessage().contains('accessible name'))
    }

    @Test
    void testInteractiveLinkWithTitle() {
        Svg svg = new Svg()
        svg.role('img').ariaLabel('Chart')

        A link = svg.addA()
        link.addTitle('Click to see details')
        link.addCircle().cx(50).cy(50).r(25).fill('blue')

        ValidationReport report = engine.validate(svg)

        assertEquals(0, report.getWarnings().size())
        assertTrue(report.isValid())
    }

    @Test
    void testInteractiveLinkWithAriaLabel() {
        Svg svg = new Svg()
        svg.role('img').ariaLabel('Chart')

        A link = svg.addA()
        link.ariaLabel('View details')
        link.addCircle().cx(50).cy(50).r(25).fill('blue')

        ValidationReport report = engine.validate(svg)

        assertEquals(0, report.getWarnings().size())
        assertTrue(report.isValid())
    }

    @Test
    void testElementWithOnclickWithoutLabel() {
        Svg svg = new Svg()
        svg.role('img').ariaLabel('Interactive chart')

        Circle circle = svg.addCircle().cx(50).cy(50).r(25)
        circle.addAttribute('onclick', 'alert("clicked")')

        ValidationReport report = engine.validate(svg)

        assertEquals(1, report.getWarnings().size())
        ValidationIssue warning = report.getWarnings().get(0)
        assertTrue(warning.getMessage().contains('Interactive element'))
        assertTrue(warning.getMessage().contains('<circle>'))
    }

    @Test
    void testElementWithHrefWithoutLabel() {
        Svg svg = new Svg()
        svg.role('img').ariaLabel('Chart')

        Circle circle = svg.addCircle().cx(50).cy(50).r(25)
        circle.addAttribute('href', '#target')

        ValidationReport report = engine.validate(svg)

        assertEquals(1, report.getWarnings().size())
        ValidationIssue warning = report.getWarnings().get(0)
        assertTrue(warning.getMessage().contains('Interactive element'))
    }

    @Test
    void testElementWithHrefWithLabel() {
        Svg svg = new Svg()
        svg.role('img').ariaLabel('Chart')

        Circle circle = svg.addCircle().cx(50).cy(50).r(25)
        circle.addAttribute('href', '#target')
        circle.ariaLabel('Clickable data point')

        ValidationReport report = engine.validate(svg)

        assertEquals(0, report.getWarnings().size())
        assertTrue(report.isValid())
    }

    @Test
    void testDecorativeElement() {
        Svg svg = new Svg()
        svg.role('img').ariaLabel('Main graphic')

        Circle circle = svg.addCircle().cx(50).cy(50).r(25)
        circle.decorative()

        ValidationReport report = engine.validate(svg)

        assertEquals(0, report.getWarnings().size())
        assertTrue(report.isValid())
    }

    @Test
    void testNestedElementWithInvalidReference() {
        Svg svg = new Svg()
        svg.role('img').ariaLabel('Chart')

        G group = svg.addG()
        group.ariaDescribedBy('invalid-ref')
        group.addCircle().cx(50).cy(50).r(25)

        ValidationReport report = engine.validate(svg)

        assertEquals(1, report.getErrors().size())
        ValidationIssue error = report.getErrors().get(0)
        assertTrue(error.getMessage().contains('aria-describedby'))
        assertTrue(error.getMessage().contains('invalid-ref'))
    }

    @Test
    void testComplexAccessibilityStructure() {
        Svg svg = new Svg()
        svg.role('img')
        svg.ariaLabelledBy('chartTitle')
        svg.ariaDescribedBy('chartDesc')

        svg.addTitle('Sales Chart').id('chartTitle')
        svg.addDesc('Monthly sales for 2024').id('chartDesc')

        G bars = svg.addG()
        bars.role('list')

        G bar1 = bars.addG()
        bar1.role('listitem')
        bar1.ariaLabel('January: $50,000')
        bar1.addRect().x(10).y(100).width(20).height(50).fill('blue')

        A link = bars.addA()
        link.ariaLabel('View details')
        link.addRect().x(40).y(120).width(20).height(30).fill('green')

        ValidationReport report = engine.validate(svg)

        assertEquals(0, report.getErrors().size())
        assertEquals(0, report.getWarnings().size())
        assertTrue(report.isValid())
    }

    @Test
    void testValidationEngineCreateAccessibility() {
        ValidationEngine accessibilityEngine = ValidationEngine.createAccessibility()

        assertEquals(1, accessibilityEngine.getRules().size())
        assertNotNull(accessibilityEngine.getRule('ACCESSIBILITY_RULE'))

        Svg svg = new Svg()
        svg.addCircle().cx(50).cy(50).r(25)

        ValidationReport report = accessibilityEngine.validate(svg)

        assertEquals(1, report.getWarnings().size())
        assertTrue(report.getWarnings().get(0).getMessage().contains('accessible name'))
    }

    @Test
    void testNonRootElementNotValidated() {
        // AccessibilityRule only validates at root Svg level
        // Create a non-root circle directly
        Svg svg = new Svg()
        svg.role('img').ariaLabel('Valid root')

        Circle circle = svg.addCircle().cx(50).cy(50).r(25)

        // Validate just the circle (not from root) - should not trigger validation
        ValidationReport report = new ValidationReport()
        rule.validate(circle, report)

        assertEquals(0, report.getAllIssues().size())
    }

    @Test
    void testEmptyAriaLabelledByIgnored() {
        Svg svg = new Svg()
        svg.role('img').ariaLabel('Chart')
        svg.ariaLabelledBy('')  // Empty string should be ignored

        ValidationReport report = engine.validate(svg)

        assertEquals(0, report.getErrors().size())
    }

    @Test
    void testWhitespaceOnlyIdReferencesIgnored() {
        Svg svg = new Svg()
        svg.role('img').ariaLabel('Chart')
        svg.ariaDescribedBy('  ')  // Whitespace only

        ValidationReport report = engine.validate(svg)

        assertEquals(0, report.getErrors().size())
    }
}
