package test.alipsa.groovy.svg

import se.alipsa.groovy.svg.Circle

import static org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Svg

class CircleTest {

    @Test
    void simpleCircleTest() {
        Svg svg = new Svg(100,100)
        Circle circle = svg.addCircle()
        .cx(50)
        .cy(50)
        .r(40)
        .stroke('green')
        .strokeWidth(4)
        .fill('yellow')

        assertEquals('<svg xmlns="http://www.w3.org/2000/svg" width="100" height="100">' +
            '<circle cx="50" cy="50" r="40" stroke="green" stroke-width="4" fill="yellow"/>' +
            '</svg>',
            svg.toXml()
        )
    }

    @Test
    void testRemoveAttributes() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle()
            .cx(50)
            .cy(50)
            .r(40)
            .fill('red')
            .stroke('blue')
            .addAttribute('data-id', '123')
            .addAttribute('data-category', 'shape')
            .addAttribute('aria-label', 'A circle')

        // Verify attributes are set
        assertEquals('123', circle.getAttribute('data-id'))
        assertEquals('shape', circle.getAttribute('data-category'))
        assertEquals('A circle', circle.getAttribute('aria-label'))

        // Remove all data- attributes
        int removed = circle.removeAttributes { it.startsWith('data-') }

        // Should have removed 2 attributes
        assertEquals(2, removed)

        // data- attributes should be gone
        assertNull(circle.getAttribute('data-id'))
        assertNull(circle.getAttribute('data-category'))

        // aria- attribute should still be there
        assertEquals('A circle', circle.getAttribute('aria-label'))

        // Other attributes should be unaffected
        assertEquals('red', circle.getAttribute('fill'))
        assertEquals('blue', circle.getAttribute('stroke'))
    }

    @Test
    void testRemoveAttributesMultipleCriteria() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle()
            .cx(50)
            .cy(50)
            .r(40)
            .addAttribute('opacity', '0.5')
            .addAttribute('transform', 'rotate(45)')
            .addAttribute('filter', 'blur(2px)')

        // Remove specific attributes by name
        int removed = circle.removeAttributes { it in ['opacity', 'transform', 'filter'] }

        assertEquals(3, removed)
        assertNull(circle.getAttribute('opacity'))
        assertNull(circle.getAttribute('transform'))
        assertNull(circle.getAttribute('filter'))

        // Positional attributes should remain
        assertEquals('50', circle.getAttribute('cx'))
        assertEquals('50', circle.getAttribute('cy'))
        assertEquals('40', circle.getAttribute('r'))
    }

    @Test
    void testRemoveAttributesNoMatches() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle().cx(50).cy(50).r(40)

        // Try to remove attributes that don't exist
        int removed = circle.removeAttributes { it.startsWith('data-') }

        assertEquals(0, removed)
    }
}
