package test.alipsa.groovy.svg.templates

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.*
import se.alipsa.groovy.svg.templates.Template

import static org.junit.jupiter.api.Assertions.*

/**
 * Tests for the Template base class.
 */
class TemplateTest {

    @Test
    void testTemplateDefaults() {
        Template template = new SimpleTemplate()
        Map defaults = template.getDefaults()

        assertEquals(0, defaults.x)
        assertEquals(0, defaults.y)
        assertEquals(100, defaults.width)
        assertEquals(100, defaults.height)
        assertEquals('blue', defaults.color)
    }

    @Test
    void testTemplateApplyWithDefaults() {
        Svg svg = new Svg(200, 200)
        SimpleTemplate template = new SimpleTemplate()

        SvgElement result = template.apply(svg)

        assertTrue(result instanceof G)
        G group = result as G
        assertEquals(1, group.getChildren().size())

        Rect rect = group.getChildren().get(0) as Rect
        assertEquals('0', rect.getX())
        assertEquals('0', rect.getY())
        assertEquals('100', rect.getWidth())
        assertEquals('100', rect.getHeight())
        assertEquals('blue', rect.getFill())
    }

    @Test
    void testTemplateApplyWithCustomParams() {
        Svg svg = new Svg(200, 200)
        SimpleTemplate template = new SimpleTemplate()

        SvgElement result = template.apply(svg, [
            x: 50,
            y: 50,
            width: 150,
            height: 80,
            color: 'red'
        ])

        G group = result as G
        Rect rect = group.getChildren().get(0) as Rect

        assertEquals('50', rect.getX())
        assertEquals('50', rect.getY())
        assertEquals('150', rect.getWidth())
        assertEquals('80', rect.getHeight())
        assertEquals('red', rect.getFill())
    }

    @Test
    void testTemplateApplyWithPartialParams() {
        Svg svg = new Svg(200, 200)
        SimpleTemplate template = new SimpleTemplate()

        // Override only some parameters
        SvgElement result = template.apply(svg, [
            x: 25,
            color: 'green'
        ])

        G group = result as G
        Rect rect = group.getChildren().get(0) as Rect

        // Overridden values
        assertEquals('25', rect.getX())
        assertEquals('green', rect.getFill())

        // Default values
        assertEquals('0', rect.getY())
        assertEquals('100', rect.getWidth())
        assertEquals('100', rect.getHeight())
    }

    @Test
    void testTemplateReuse() {
        Svg svg = new Svg(400, 400)
        SimpleTemplate template = new SimpleTemplate()

        // Apply template multiple times with different params
        template.apply(svg, [x: 10, y: 10, color: 'red'])
        template.apply(svg, [x: 120, y: 10, color: 'green'])
        template.apply(svg, [x: 230, y: 10, color: 'blue'])

        assertEquals(3, svg.getChildren().size())

        G group1 = svg.getChildren().get(0) as G
        Rect rect1 = group1.getChildren().get(0) as Rect
        assertEquals('red', rect1.getFill())

        G group2 = svg.getChildren().get(1) as G
        Rect rect2 = group2.getChildren().get(0) as Rect
        assertEquals('green', rect2.getFill())

        G group3 = svg.getChildren().get(2) as G
        Rect rect3 = group3.getChildren().get(0) as Rect
        assertEquals('blue', rect3.getFill())
    }

    @Test
    void testTemplateMergeParams() {
        SimpleTemplate template = new SimpleTemplate()

        // Test merge with empty params
        Map merged1 = template.mergeParams([:])
        assertEquals(template.getDefaults(), merged1)

        // Test merge with null
        Map merged2 = template.mergeParams(null)
        assertEquals(template.getDefaults(), merged2)

        // Test merge with partial override
        Map merged3 = template.mergeParams([x: 50, color: 'red'])
        assertEquals(50, merged3.x)
        assertEquals('red', merged3.color)
        assertEquals(0, merged3.y)  // From defaults
        assertEquals(100, merged3.width)  // From defaults
    }

    @Test
    void testTemplateGeneratedSvgIsValid() {
        Svg svg = new Svg(400, 400)
        SimpleTemplate template = new SimpleTemplate()

        template.apply(svg, [x: 50, y: 50, color: 'purple'])

        String xml = svg.toXml()
        assertTrue(xml.contains('<g>'))
        assertTrue(xml.contains('x="50"'))
        assertTrue(xml.contains('y="50"'))
        assertTrue(xml.contains('fill="purple"'))
        assertTrue(xml.contains('</g>'))
    }

    /**
     * Simple template implementation for testing.
     */
    static class SimpleTemplate extends Template {

        @Override
        SvgElement apply(AbstractElementContainer parent, Map params) {
            Map merged = mergeParams(params)

            G group = parent.addG()
            group.addRect()
                .x(merged.x as Number)
                .y(merged.y as Number)
                .width(merged.width as Number)
                .height(merged.height as Number)
                .fill(merged.color as String)

            return group
        }

        @Override
        Map getDefaults() {
            return [
                x: 0,
                y: 0,
                width: 100,
                height: 100,
                color: 'blue'
            ]
        }
    }
}
