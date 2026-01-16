package test.alipsa.groovy.svg.templates

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.*
import se.alipsa.groovy.svg.templates.ChartLegend

import static org.junit.jupiter.api.Assertions.*

/**
 * Tests for the ChartLegend template.
 */
class ChartLegendTest {

    @Test
    void testChartLegendDefaults() {
        ChartLegend legend = new ChartLegend()
        Map defaults = legend.getDefaults()

        assertEquals(0, defaults.x)
        assertEquals(0, defaults.y)
        assertEquals([], defaults.items)
        assertEquals(15, defaults.swatchSize)
        assertEquals(25, defaults.spacing)
        assertEquals(14, defaults.fontSize)
        assertEquals(10, defaults.padding)
        assertTrue(defaults.showBorder as boolean)
        assertEquals('black', defaults.borderColor)
        assertEquals(1, defaults.borderWidth)
        assertEquals('white', defaults.backgroundColor)
        assertEquals(0.9, defaults.backgroundOpacity)
    }

    @Test
    void testChartLegendWithItems() {
        Svg svg = new Svg(600, 400)
        ChartLegend legend = new ChartLegend()

        def items = [
            [color: 'red', label: 'Product A'],
            [color: 'blue', label: 'Product B'],
            [color: 'green', label: 'Product C']
        ]

        SvgElement result = legend.apply(svg, [
            x: 450,
            y: 50,
            items: items
        ])

        assertTrue(result instanceof G)
        G group = result as G

        // Should have: 1 background + 3*(swatch + label) = 1 + 6 = 7 children
        assertTrue(group.getChildren().size() >= 7)

        // Check background rect
        Rect background = group.getChildren().get(0) as Rect
        assertEquals('450', background.getX())
        assertEquals('50', background.getY())
        assertEquals('white', background.getFill())
        assertEquals('0.9', background.getFillOpacity())
        assertEquals('black', background.getStroke())
    }

    @Test
    void testChartLegendWithoutBorder() {
        Svg svg = new Svg(600, 400)
        ChartLegend legend = new ChartLegend()

        def items = [
            [color: 'red', label: 'Item 1']
        ]

        SvgElement result = legend.apply(svg, [
            x: 100,
            y: 100,
            items: items,
            showBorder: false
        ])

        G group = result as G
        Rect background = group.getChildren().get(0) as Rect

        assertEquals('none', background.getStroke())
    }

    @Test
    void testChartLegendCustomStyling() {
        Svg svg = new Svg(600, 400)
        ChartLegend legend = new ChartLegend()

        def items = [
            [color: 'purple', label: 'Custom Item']
        ]

        SvgElement result = legend.apply(svg, [
            x: 200,
            y: 200,
            items: items,
            swatchSize: 20,
            spacing: 30,
            fontSize: 16,
            padding: 15,
            borderColor: 'navy',
            borderWidth: 2,
            backgroundColor: 'lightgray',
            backgroundOpacity: 0.8
        ])

        G group = result as G
        Rect background = group.getChildren().get(0) as Rect

        assertEquals('200', background.getX())
        assertEquals('200', background.getY())
        assertEquals('lightgray', background.getFill())
        assertEquals('0.8', background.getFillOpacity())
        assertEquals('navy', background.getStroke())
        assertEquals('2', background.getStrokeWidth())
    }

    @Test
    void testChartLegendGeneratedStructure() {
        Svg svg = new Svg(600, 400)
        ChartLegend legend = new ChartLegend()

        def items = [
            [color: '#FF0000', label: 'Red Item'],
            [color: '#00FF00', label: 'Green Item']
        ]

        legend.apply(svg, [
            x: 50,
            y: 50,
            items: items
        ])

        String xml = svg.toXml()

        // Check structure
        assertTrue(xml.contains('<g>'))
        assertTrue(xml.contains('fill="white"'))  // Background
        assertTrue(xml.contains('fill="#FF0000"'))  // First swatch
        assertTrue(xml.contains('fill="#00FF00"'))  // Second swatch
        assertTrue(xml.contains('Red Item'))  // First label
        assertTrue(xml.contains('Green Item'))  // Second label
    }

    @Test
    void testChartLegendWithId() {
        Svg svg = new Svg(600, 400)
        ChartLegend legend = new ChartLegend()

        def items = [
            [color: 'blue', label: 'Test']
        ]

        SvgElement result = legend.apply(svg, [
            x: 10,
            y: 10,
            items: items,
            id: 'myLegend'
        ])

        G group = result as G
        assertEquals('myLegend', group.getId())
    }

    @Test
    void testChartLegendMultipleLegends() {
        Svg svg = new Svg(800, 600)
        ChartLegend legend = new ChartLegend()

        // First legend
        legend.apply(svg, [
            x: 50,
            y: 50,
            items: [[color: 'red', label: 'Series 1']],
            id: 'legend1'
        ])

        // Second legend
        legend.apply(svg, [
            x: 400,
            y: 50,
            items: [[color: 'blue', label: 'Series 2']],
            id: 'legend2'
        ])

        assertEquals(2, svg.getChildren().size())

        G group1 = svg.getChildren().get(0) as G
        assertEquals('legend1', group1.getId())

        G group2 = svg.getChildren().get(1) as G
        assertEquals('legend2', group2.getId())
    }

    @Test
    void testChartLegendEmptyItems() {
        Svg svg = new Svg(600, 400)
        ChartLegend legend = new ChartLegend()

        // Apply with empty items list
        SvgElement result = legend.apply(svg, [
            x: 100,
            y: 100,
            items: []
        ])

        assertTrue(result instanceof G)
        G group = result as G

        // Should have at least the background rect
        assertTrue(group.getChildren().size() >= 1)
    }

    @Test
    void testChartLegendIntegrationWithChart() {
        // Simulate a real use case: bar chart with legend
        Svg svg = new Svg(700, 500)
        svg.role('img').ariaLabel('Sales Chart with Legend')

        // Create some bars
        def chartData = [
            [x: 50, height: 100, color: 'red', label: 'Q1 Sales'],
            [x: 150, height: 150, color: 'blue', label: 'Q2 Sales'],
            [x: 250, height: 120, color: 'green', label: 'Q3 Sales']
        ]

        chartData.each { data ->
            svg.addRect()
                .x(data.x as Number)
                .y(350 - (data.height as Number))
                .width(50)
                .height(data.height as Number)
                .fill(data.color as String)
        }

        // Add legend
        ChartLegend legend = new ChartLegend()
        def legendItems = chartData.collect { [color: it.color, label: it.label] }

        legend.apply(svg, [
            x: 450,
            y: 100,
            items: legendItems
        ])

        // Verify structure
        assertNotNull(svg)
        assertEquals(4, svg.getChildren().size())  // 3 bars + 1 legend group

        // Verify svg has accessibility
        assertEquals('img', svg.getRole())
        assertEquals('Sales Chart with Legend', svg.getAriaLabel())
    }
}
