package test.alipsa.groovy.svg

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Svg
import se.alipsa.groovy.svg.Circle
import se.alipsa.groovy.svg.Rect
import se.alipsa.groovy.svg.G

import static org.junit.jupiter.api.Assertions.*

/**
 * Tests for ARIA accessibility helper methods on SvgElement.
 */
class AccessibilityHelpersTest {

    private Svg svg

    @BeforeEach
    void setUp() {
        svg = new Svg()
    }

    @Test
    void testRole() {
        svg.role('img')
        assertEquals('img', svg.getRole())
        assertTrue(svg.toXml().contains('role="img"'))
    }

    @Test
    void testRoleChaining() {
        Circle circle = svg.addCircle().cx(50).cy(50).r(25)
        Circle result = circle.role('button')
        assertSame(circle, result, 'role() should return this for chaining')
        assertEquals('button', circle.getRole())
    }

    @Test
    void testAriaLabel() {
        svg.ariaLabel('My accessible SVG graphic')
        assertEquals('My accessible SVG graphic', svg.getAriaLabel())
        assertTrue(svg.toXml().contains('aria-label="My accessible SVG graphic"'))
    }

    @Test
    void testAriaLabelChaining() {
        Rect rect = svg.addRect().x(10).y(10).width(100).height(50)
        Rect result = rect.ariaLabel('Important rectangle')
        assertSame(rect, result, 'ariaLabel() should return this for chaining')
        assertEquals('Important rectangle', rect.getAriaLabel())
    }

    @Test
    void testAriaLabelledBy() {
        svg.ariaLabelledBy('title1 desc1')
        assertEquals('title1 desc1', svg.getAriaLabelledBy())
        assertTrue(svg.toXml().contains('aria-labelledby="title1 desc1"'))
    }

    @Test
    void testAriaLabelledByChaining() {
        G group = svg.addG()
        G result = group.ariaLabelledBy('label1')
        assertSame(group, result, 'ariaLabelledBy() should return this for chaining')
        assertEquals('label1', group.getAriaLabelledBy())
    }

    @Test
    void testAriaDescribedBy() {
        svg.ariaDescribedBy('desc1 desc2')
        assertEquals('desc1 desc2', svg.getAriaDescribedBy())
        assertTrue(svg.toXml().contains('aria-describedby="desc1 desc2"'))
    }

    @Test
    void testAriaDescribedByChaining() {
        Circle circle = svg.addCircle().cx(50).cy(50).r(25)
        Circle result = circle.ariaDescribedBy('description1')
        assertSame(circle, result, 'ariaDescribedBy() should return this for chaining')
        assertEquals('description1', circle.getAriaDescribedBy())
    }

    @Test
    void testAriaHidden() {
        svg.ariaHidden(true)
        assertTrue(svg.isAriaHidden())
        assertTrue(svg.toXml().contains('aria-hidden="true"'))

        svg.ariaHidden(false)
        assertFalse(svg.isAriaHidden())
        assertTrue(svg.toXml().contains('aria-hidden="false"'))
    }

    @Test
    void testAriaHiddenChaining() {
        Rect rect = svg.addRect().x(10).y(10).width(100).height(50)
        Rect result = rect.ariaHidden(true)
        assertSame(rect, result, 'ariaHidden() should return this for chaining')
        assertTrue(rect.isAriaHidden())
    }

    @Test
    void testAriaLive() {
        svg.ariaLive('polite')
        assertEquals('polite', svg.getAriaLive())
        assertTrue(svg.toXml().contains('aria-live="polite"'))

        svg.ariaLive('assertive')
        assertEquals('assertive', svg.getAriaLive())
    }

    @Test
    void testAriaLiveChaining() {
        G group = svg.addG()
        G result = group.ariaLive('off')
        assertSame(group, result, 'ariaLive() should return this for chaining')
        assertEquals('off', group.getAriaLive())
    }

    @Test
    void testDecorativeConvenience() {
        Circle circle = svg.addCircle().cx(50).cy(50).r(25)
        Circle result = circle.decorative()

        assertSame(circle, result, 'decorative() should return this for chaining')
        assertEquals('presentation', circle.getRole())
        assertTrue(circle.isAriaHidden())

        String output = circle.toXml()
        assertTrue(output.contains('role="presentation"'))
        assertTrue(output.contains('aria-hidden="true"'))
    }

    @Test
    void testAccessibleImageConvenience() {
        Rect rect = svg.addRect().x(10).y(10).width(100).height(50)
        Rect result = rect.accessibleImage('A red rectangle')

        assertSame(rect, result, 'accessibleImage() should return this for chaining')
        assertEquals('img', rect.getRole())
        assertEquals('A red rectangle', rect.getAriaLabel())

        String output = rect.toXml()
        assertTrue(output.contains('role="img"'))
        assertTrue(output.contains('aria-label="A red rectangle"'))
    }

    @Test
    void testMethodChainingCombination() {
        Circle circle = svg.addCircle().cx(50).cy(50).r(25)
            .role('img')
            .ariaLabel('My circle')
            .ariaDescribedBy('desc1')
            .fill('blue')

        assertEquals('img', circle.getRole())
        assertEquals('My circle', circle.getAriaLabel())
        assertEquals('desc1', circle.getAriaDescribedBy())

        String output = circle.toXml()
        assertTrue(output.contains('role="img"'))
        assertTrue(output.contains('aria-label="My circle"'))
        assertTrue(output.contains('aria-describedby="desc1"'))
        assertTrue(output.contains('fill="blue"'))
    }

    @Test
    void testGettersReturnNullWhenNotSet() {
        Circle circle = svg.addCircle().cx(50).cy(50).r(25)

        assertNull(circle.getRole())
        assertNull(circle.getAriaLabel())
        assertNull(circle.getAriaLabelledBy())
        assertNull(circle.getAriaDescribedBy())
        assertNull(circle.getAriaLive())
        assertFalse(circle.isAriaHidden())
    }

    @Test
    void testCompleteAccessibilityPattern() {
        svg.role('img')
            .ariaLabel('A complex chart showing sales data')
            .ariaDescribedBy('chartDesc')

        svg.addTitle('Sales Chart')
        svg.addDesc('A bar chart showing monthly sales for 2024').id('chartDesc')

        Circle dataPoint = svg.addCircle().cx(100).cy(100).r(5)
            .role('img')
            .ariaLabel('January: $50,000')
            .fill('red')

        String output = svg.toXml()
        assertTrue(output.contains('role="img"'))
        assertTrue(output.contains('aria-label="A complex chart showing sales data"'))
        assertTrue(output.contains('aria-describedby="chartDesc"'))
        assertTrue(output.contains('<title>Sales Chart</title>'))
        assertTrue(output.contains('id="chartDesc"'))
    }
}
