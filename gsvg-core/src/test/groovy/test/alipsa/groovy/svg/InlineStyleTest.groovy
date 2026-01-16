package test.alipsa.groovy.svg

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Circle
import se.alipsa.groovy.svg.Rect
import se.alipsa.groovy.svg.Svg

import static org.junit.jupiter.api.Assertions.*

/**
 * Tests for SvgElement inline style and class management.
 */
class InlineStyleTest {

    @Test
    void testStyleWithMap() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle()
            .cx(50).cy(50).r(40)
            .style([fill: 'red', stroke: 'black', 'stroke-width': '2'])

        String styleAttr = circle.getAttribute('style')
        assertNotNull(styleAttr)
        assertTrue(styleAttr.contains('fill: red'))
        assertTrue(styleAttr.contains('stroke: black'))
        assertTrue(styleAttr.contains('stroke-width: 2'))
    }

    @Test
    void testStyleWithMapEmpty() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle().style([:])

        assertEquals('', circle.getAttribute('style'))
    }

    @Test
    void testStyleWithMapNull() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle().style((Map) null)

        assertEquals('', circle.getAttribute('style'))
    }

    @Test
    void testGetStyleMap() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle()
            .style('fill: red; stroke: black; opacity: 0.8;')

        Map<String, String> styleMap = circle.getStyleMap()

        assertEquals(3, styleMap.size())
        assertEquals('red', styleMap['fill'])
        assertEquals('black', styleMap['stroke'])
        assertEquals('0.8', styleMap['opacity'])
    }

    @Test
    void testGetStyleMapEmpty() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle()

        Map<String, String> styleMap = circle.getStyleMap()

        assertTrue(styleMap.isEmpty())
    }

    @Test
    void testGetStyleProperty() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle()
            .style('fill: blue; stroke: green;')

        assertEquals('blue', circle.getStyleProperty('fill'))
        assertEquals('green', circle.getStyleProperty('stroke'))
        assertNull(circle.getStyleProperty('opacity'))
    }

    @Test
    void testStyleMapRoundTrip() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle()
            .style([fill: 'red', stroke: 'black', opacity: '0.5'])

        Map<String, String> styleMap = circle.getStyleMap()

        assertEquals('red', styleMap['fill'])
        assertEquals('black', styleMap['stroke'])
        assertEquals('0.5', styleMap['opacity'])
    }

    @Test
    void testBackwardCompatibilityStringStyle() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle()
            .style('fill: red; stroke: black;')

        String styleAttr = circle.getAttribute('style')
        assertEquals('fill: red; stroke: black;', styleAttr)
    }

    @Test
    void testAddClass() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle()
            .addClass('highlight')

        assertEquals('highlight', circle.getAttribute('class'))
        assertTrue(circle.hasClass('highlight'))
    }

    @Test
    void testAddMultipleClasses() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle()
            .addClass('highlight')
            .addClass('active')
            .addClass('primary')

        String classAttr = circle.getAttribute('class')
        assertTrue(classAttr.contains('highlight'))
        assertTrue(classAttr.contains('active'))
        assertTrue(classAttr.contains('primary'))

        assertTrue(circle.hasClass('highlight'))
        assertTrue(circle.hasClass('active'))
        assertTrue(circle.hasClass('primary'))
    }

    @Test
    void testAddClassDuplicate() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle()
            .addClass('test')
            .addClass('test')

        String classAttr = circle.getAttribute('class')
        assertEquals('test', classAttr)

        List<String> classes = circle.getClasses()
        assertEquals(1, classes.size())
    }

    @Test
    void testRemoveClass() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle()
            .addClass('highlight')
            .addClass('active')
            .addClass('primary')

        circle.removeClass('active')

        assertTrue(circle.hasClass('highlight'))
        assertFalse(circle.hasClass('active'))
        assertTrue(circle.hasClass('primary'))

        String classAttr = circle.getAttribute('class')
        assertTrue(classAttr.contains('highlight'))
        assertFalse(classAttr.contains('active'))
        assertTrue(classAttr.contains('primary'))
    }

    @Test
    void testRemoveClassNonExistent() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle()
            .addClass('test')

        circle.removeClass('nonexistent')

        assertTrue(circle.hasClass('test'))
        assertEquals('test', circle.getAttribute('class'))
    }

    @Test
    void testRemoveLastClass() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle()
            .addClass('test')

        circle.removeClass('test')

        assertFalse(circle.hasClass('test'))
        assertEquals('', circle.getAttribute('class'))
    }

    @Test
    void testToggleClass() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle()

        // Toggle on
        circle.toggleClass('active')
        assertTrue(circle.hasClass('active'))

        // Toggle off
        circle.toggleClass('active')
        assertFalse(circle.hasClass('active'))

        // Toggle on again
        circle.toggleClass('active')
        assertTrue(circle.hasClass('active'))
    }

    @Test
    void testHasClassEmpty() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle()

        assertFalse(circle.hasClass('anything'))
        assertFalse(circle.hasClass(''))
        assertFalse(circle.hasClass(null))
    }

    @Test
    void testGetClasses() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle()
            .addClass('highlight')
            .addClass('active')
            .addClass('primary')

        List<String> classes = circle.getClasses()

        assertEquals(3, classes.size())
        assertTrue(classes.contains('highlight'))
        assertTrue(classes.contains('active'))
        assertTrue(classes.contains('primary'))
    }

    @Test
    void testGetClassesEmpty() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle()

        List<String> classes = circle.getClasses()

        assertTrue(classes.isEmpty())
    }

    @Test
    void testBackwardCompatibilityStyleClass() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle()
            .styleClass('highlight active')

        String classAttr = circle.getAttribute('class')
        assertEquals('highlight active', classAttr)

        // Can still use new methods
        assertTrue(circle.hasClass('highlight'))
        assertTrue(circle.hasClass('active'))

        List<String> classes = circle.getClasses()
        assertEquals(2, classes.size())
    }

    @Test
    void testComplexScenario() {
        Svg svg = new Svg(200, 200)
        Rect rect = svg.addRect()
            .x(10).y(10).width(100).height(100)
            .style([fill: 'blue', stroke: 'black', 'stroke-width': '2'])
            .addClass('interactive')
            .addClass('highlighted')
            .id('main-rect')

        String xml = rect.toXml()

        assertTrue(xml.contains('style="'))
        assertTrue(xml.contains('fill: blue'))
        assertTrue(xml.contains('class="'))
        assertTrue(xml.contains('interactive'))
        assertTrue(xml.contains('id="main-rect"'))
    }

    @Test
    void testChaining() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle()
            .cx(50).cy(50).r(40)
            .style([fill: 'red'])
            .addClass('highlight')
            .addClass('active')
            .style([stroke: 'black'])  // This should replace the style
            .removeClass('active')

        // Last style() call replaces previous
        Map<String, String> styleMap = circle.getStyleMap()
        assertEquals('black', styleMap['stroke'])
        assertNull(styleMap['fill'])  // Was replaced

        assertTrue(circle.hasClass('highlight'))
        assertFalse(circle.hasClass('active'))
    }

    @Test
    void testAddClassWithWhitespace() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle()

        circle.addClass('  ')
        circle.addClass('')
        circle.addClass(null)

        List<String> classes = circle.getClasses()
        assertTrue(classes.isEmpty())
    }

    @Test
    void testRemoveClassWithWhitespace() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle()
            .addClass('test')

        circle.removeClass('  ')
        circle.removeClass('')
        circle.removeClass(null)

        // Class should still be there
        assertTrue(circle.hasClass('test'))
    }
}
