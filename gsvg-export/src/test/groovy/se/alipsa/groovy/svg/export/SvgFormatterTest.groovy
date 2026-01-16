package se.alipsa.groovy.svg.export

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.*

import static org.junit.jupiter.api.Assertions.*

class SvgFormatterTest {

    @Test
    void testPrettifyWithDefaultOptions() {
        Svg svg = new Svg(200, 200)
        svg.addCircle().cx(100).cy(100).r(50).fill('red')

        String formatted = SvgFormatter.prettify(svg, [:])

        assertNotNull(formatted)
        assertTrue(formatted.startsWith('<?xml version="1.0" encoding="UTF-8"?>'))
        assertTrue(formatted.contains('<svg'))
        assertTrue(formatted.contains('<circle'))
        assertTrue(formatted.contains('  '))  // Should have indentation
        assertTrue(formatted.contains('\n'))  // Should have newlines
    }

    @Test
    void testPrettifyWithCustomIndent() {
        Svg svg = new Svg(200, 200)
        svg.addRect().x(10).y(10).width(100).height(100)

        String formatted = SvgFormatter.prettify(svg, [indent: '    '])

        assertTrue(formatted.contains('    <rect'))  // 4-space indentation
    }

    @Test
    void testPrettifyWithCustomNewline() {
        Svg svg = new Svg(200, 200)
        svg.addCircle().cx(100).cy(100).r(50)

        String formatted = SvgFormatter.prettify(svg, [newline: '\r\n'])

        assertTrue(formatted.contains('\r\n'))
    }

    @Test
    void testPrettifyWithSortedAttributes() {
        Svg svg = new Svg(200, 200)
        Circle circle = svg.addCircle()
        // Add attributes in reverse alphabetical order
        circle.r(50)
        circle.fill('red')
        circle.cy(100)
        circle.cx(100)

        String formatted = SvgFormatter.prettify(svg, [sortAttributes: true])

        // Attributes should be sorted: cx, cy, fill, r
        int cxPos = formatted.indexOf('cx=')
        int cyPos = formatted.indexOf('cy=')
        int fillPos = formatted.indexOf('fill=')
        int rPos = formatted.indexOf('r=')

        assertTrue(cxPos < cyPos)
        assertTrue(cyPos < fillPos)
        assertTrue(fillPos < rPos)
    }

    @Test
    void testPrettifyWithGroupElements() {
        Svg svg = new Svg(300, 300)

        // Add multiple circles
        svg.addCircle().cx(50).cy(50).r(25)
        svg.addCircle().cx(100).cy(100).r(25)

        // Add a rectangle
        svg.addRect().x(10).y(10).width(50).height(50)

        // Add another circle
        svg.addCircle().cx(150).cy(150).r(25)

        String formatted = SvgFormatter.prettify(svg, [groupElements: true])

        // Should have extra newlines between different element types
        assertTrue(formatted.contains('\n\n'))
    }

    @Test
    void testPrettifyNestedElements() {
        Svg svg = new Svg(200, 200)
        G group = svg.addG().fill('blue')
        group.addCircle().cx(50).cy(50).r(25)
        group.addCircle().cx(150).cy(150).r(25)

        String formatted = SvgFormatter.prettify(svg, [:])

        // Check nesting
        assertTrue(formatted.contains('<g'))
        assertTrue(formatted.contains('<circle'))
        assertTrue(formatted.contains('</g>'))

        // Circles should be more indented than group
        String[] lines = formatted.split('\n')
        String groupLine = lines.find { it.contains('<g') }
        String circleLine = lines.find { it.contains('<circle') }

        int groupIndent = groupLine.indexOf('<')
        int circleIndent = circleLine.indexOf('<')

        assertTrue(circleIndent > groupIndent)
    }

    @Test
    void testPrettifyComplexStructure() {
        Svg svg = new Svg(300, 300)

        Defs defs = svg.addDefs()
        LinearGradient gradient = defs.addLinearGradient().id('grad1')
        gradient.addStop().offset(0).stopColor('red')
        gradient.addStop().offset(1).stopColor('blue')

        G group = svg.addG().fill('url(#grad1)')
        group.addCircle().cx(150).cy(150).r(100)
        group.addRect().x(50).y(50).width(200).height(200)

        String formatted = SvgFormatter.prettify(svg, [:])

        assertTrue(formatted.contains('<defs'))
        assertTrue(formatted.contains('<linearGradient'))
        assertTrue(formatted.contains('<stop'))
        assertTrue(formatted.contains('<g'))
        assertTrue(formatted.contains('<circle'))
        assertTrue(formatted.contains('<rect'))
    }

    @Test
    void testPrettyPrintDefaultMethod() {
        Svg svg = new Svg(100, 100)
        svg.addCircle().cx(50).cy(50).r(40)

        String formatted = SvgFormatter.prettyPrint(svg)

        assertNotNull(formatted)
        assertTrue(formatted.contains('<?xml'))
        assertTrue(formatted.contains('  '))  // Default 2-space indent
    }

    @Test
    void testPrettyPrintWithIndentSize() {
        Svg svg = new Svg(100, 100)
        svg.addRect().x(10).y(10).width(80).height(80)

        String formatted = SvgFormatter.prettyPrint(svg, 4)

        assertTrue(formatted.contains('    <rect'))  // 4-space indent
    }

    @Test
    void testPrettifyWithSortedAttributesMethod() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle()
        circle.r(40)
        circle.cy(50)
        circle.cx(50)

        String formatted = SvgFormatter.prettifyWithSortedAttributes(svg)

        // Attributes should be sorted
        int cxPos = formatted.indexOf('cx=')
        int cyPos = formatted.indexOf('cy=')
        int rPos = formatted.indexOf('r=')

        assertTrue(cxPos < cyPos)
        assertTrue(cyPos < rPos)
    }

    @Test
    void testPrettifyWithGroupingMethod() {
        Svg svg = new Svg(200, 200)
        svg.addCircle().cx(50).cy(50).r(25)
        svg.addCircle().cx(100).cy(100).r(25)
        svg.addRect().x(10).y(10).width(50).height(50)

        String formatted = SvgFormatter.prettifyWithGrouping(svg)

        assertTrue(formatted.contains('\n\n'))  // Extra newlines for grouping
    }

    @Test
    void testFormatMethod() {
        Svg svg = new Svg(100, 100)
        svg.addCircle().cx(50).cy(50).r(40)

        Svg result = SvgFormatter.format(svg, [:])

        // Should return the same instance
        assertSame(svg, result)
    }

    @Test
    void testEscapeXml() {
        Svg svg = new Svg(100, 100)
        // Add text with special characters
        Text text = svg.addText().x(10).y(50)
        text.addAttribute('data-test', '<>&"\'')

        String formatted = SvgFormatter.prettify(svg, [:])

        // Special characters should be escaped
        assertTrue(formatted.contains('&lt;'))
        assertTrue(formatted.contains('&gt;'))
        assertTrue(formatted.contains('&amp;'))
        assertTrue(formatted.contains('&quot;'))
        assertTrue(formatted.contains('&apos;'))
    }

    @Test
    void testSelfClosingTags() {
        Svg svg = new Svg(100, 100)
        svg.addCircle().cx(50).cy(50).r(40)
        svg.addRect().x(10).y(10).width(80).height(80)

        String formatted = SvgFormatter.prettify(svg, [:])

        // Self-closing tags should end with />
        assertTrue(formatted.contains('<circle') && formatted.contains('/>'))
        assertTrue(formatted.contains('<rect') && formatted.contains('/>'))
    }

    @Test
    void testMultipleLevelsOfNesting() {
        Svg svg = new Svg(200, 200)
        G g1 = svg.addG().id('g1')
        G g2 = g1.addG().id('g2')
        G g3 = g2.addG().id('g3')
        g3.addCircle().cx(100).cy(100).r(50)

        String formatted = SvgFormatter.prettify(svg, [indent: '  '])

        // Check progressive indentation
        String[] lines = formatted.split('\n')
        String g1Line = lines.find { it.contains('id="g1"') }
        String g2Line = lines.find { it.contains('id="g2"') }
        String g3Line = lines.find { it.contains('id="g3"') }
        String circleLine = lines.find { it.contains('<circle') }

        int g1Indent = g1Line.indexOf('<g')
        int g2Indent = g2Line.indexOf('<g')
        int g3Indent = g3Line.indexOf('<g')
        int circleIndent = circleLine.indexOf('<circle')

        assertTrue(g2Indent > g1Indent)
        assertTrue(g3Indent > g2Indent)
        assertTrue(circleIndent > g3Indent)
    }
}
