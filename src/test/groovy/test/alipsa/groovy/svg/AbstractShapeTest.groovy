package test.alipsa.groovy.svg

import se.alipsa.groovy.svg.Circle
import se.alipsa.groovy.svg.Path
import se.alipsa.groovy.svg.Rect
import se.alipsa.groovy.svg.Svg

import static org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class AbstractShapeTest {

    @Test
    void testStrokeDasharray() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle()
                .cx(50).cy(50).r(40)
                .strokeDasharray('5 5')

        assertEquals('5 5', circle.strokeDasharray)
        assertEquals('5 5', circle.getStrokeDasharray())
        assertTrue(circle.toXml().contains('stroke-dasharray="5 5"'))
    }

    @Test
    void testStrokeDasharrayComplex() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle()
                .strokeDasharray('10 5 2 5')

        assertEquals('10 5 2 5', circle.strokeDasharray)
        assertTrue(circle.toXml().contains('stroke-dasharray="10 5 2 5"'))
    }

    @Test
    void testStrokeDashoffsetNumber() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle()
                .strokeDashoffset(2.5)

        assertEquals('2.5', circle.strokeDashoffset)
        assertEquals('2.5', circle.getStrokeDashoffset())
        assertTrue(circle.toXml().contains('stroke-dashoffset="2.5"'))
    }

    @Test
    void testStrokeDashoffsetString() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle()
                .strokeDashoffset('5px')

        assertEquals('5px', circle.strokeDashoffset)
        assertTrue(circle.toXml().contains('stroke-dashoffset="5px"'))
    }

    @Test
    void testStrokeLinejoin() {
        Svg svg = new Svg(100, 100)

        // Test 'miter'
        Rect rect1 = svg.addRect()
                .strokeLinejoin('miter')
        assertEquals('miter', rect1.strokeLinejoin)
        assertEquals('miter', rect1.getStrokeLinejoin())

        // Test 'round'
        Rect rect2 = svg.addRect()
                .strokeLinejoin('round')
        assertEquals('round', rect2.strokeLinejoin)

        // Test 'bevel'
        Rect rect3 = svg.addRect()
                .strokeLinejoin('bevel')
        assertEquals('bevel', rect3.strokeLinejoin)
        assertTrue(rect3.toXml().contains('stroke-linejoin="bevel"'))
    }

    @Test
    void testStrokeLinecap() {
        Svg svg = new Svg(100, 100)

        // Test 'butt'
        Circle circle1 = svg.addCircle()
                .strokeLinecap('butt')
        assertEquals('butt', circle1.strokeLinecap)
        assertEquals('butt', circle1.getStrokeLinecap())

        // Test 'round'
        Circle circle2 = svg.addCircle()
                .strokeLinecap('round')
        assertEquals('round', circle2.strokeLinecap)

        // Test 'square'
        Circle circle3 = svg.addCircle()
                .strokeLinecap('square')
        assertEquals('square', circle3.strokeLinecap)
        assertTrue(circle3.toXml().contains('stroke-linecap="square"'))
    }

    @Test
    void testStrokeMiterlimitNumber() {
        Svg svg = new Svg(100, 100)
        Rect rect = svg.addRect()
                .strokeLinejoin('miter')
                .strokeMiterlimit(4)

        assertEquals('4', rect.strokeMiterlimit)
        assertEquals('4', rect.getStrokeMiterlimit())
        assertTrue(rect.toXml().contains('stroke-miterlimit="4"'))
    }

    @Test
    void testStrokeMiterlimitString() {
        Svg svg = new Svg(100, 100)
        Rect rect = svg.addRect()
                .strokeMiterlimit('10')

        assertEquals('10', rect.strokeMiterlimit)
        assertTrue(rect.toXml().contains('stroke-miterlimit="10"'))
    }

    @Test
    void testStrokeOpacityNumber() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle()
                .cx(50).cy(50).r(40)
                .strokeOpacity(0.5)

        assertEquals('0.5', circle.strokeOpacity)
        assertEquals('0.5', circle.getStrokeOpacity())
        assertTrue(circle.toXml().contains('stroke-opacity="0.5"'))
    }

    @Test
    void testStrokeOpacityString() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle()
                .strokeOpacity('50%')

        assertEquals('50%', circle.strokeOpacity)
        assertTrue(circle.toXml().contains('stroke-opacity="50%"'))
    }

    @Test
    void testFillOpacityNumber() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle()
                .cx(50).cy(50).r(40)
                .fillOpacity(0.8)

        assertEquals('0.8', circle.fillOpacity)
        assertEquals('0.8', circle.getFillOpacity())
        assertTrue(circle.toXml().contains('fill-opacity="0.8"'))
    }

    @Test
    void testFillOpacityString() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle()
                .fillOpacity('80%')

        assertEquals('80%', circle.fillOpacity)
        assertTrue(circle.toXml().contains('fill-opacity="80%"'))
    }

    @Test
    void testFillOpacityAndStrokeOpacityDifferent() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle()
                .cx(50).cy(50).r(40)
                .fillOpacity(0.3)
                .strokeOpacity(0.9)

        assertEquals('0.3', circle.fillOpacity)
        assertEquals('0.9', circle.strokeOpacity)

        String xml = circle.toXml()
        assertTrue(xml.contains('fill-opacity="0.3"'))
        assertTrue(xml.contains('stroke-opacity="0.9"'))
    }

    @Test
    void testFillRule() {
        Svg svg = new Svg(100, 100)

        // Test 'nonzero'
        Path path1 = svg.addPath()
                .d('M0,0 L50,0 L50,50 L0,50 Z')
                .fillRule('nonzero')
        assertEquals('nonzero', path1.fillRule)
        assertEquals('nonzero', path1.getFillRule())

        // Test 'evenodd'
        Path path2 = svg.addPath()
                .d('M0,0 L50,0 L50,50 L0,50 Z M25,25 L25,40 L40,40 L40,25 Z')
                .fillRule('evenodd')
        assertEquals('evenodd', path2.fillRule)
        assertTrue(path2.toXml().contains('fill-rule="evenodd"'))
    }

    @Test
    void testMask() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle()
                .cx(50).cy(50).r(40)
                .mask('url(#myMask)')

        assertEquals('url(#myMask)', circle.mask)
        assertEquals('url(#myMask)', circle.getMask())
        assertTrue(circle.toXml().contains('mask="url(#myMask)"'))
    }

    @Test
    void testOnClick() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle()
                .cx(50).cy(50).r(40)
                .onClick('handleClick(evt)')

        assertTrue(circle.toXml().contains('onclick="handleClick(evt)"'))
    }

    @Test
    void testComplexStrokeStyling() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle()
                .cx(50).cy(50).r(40)
                .stroke('blue')
                .strokeWidth(2)
                .strokeDasharray('5 10')
                .strokeDashoffset(2)
                .strokeLinecap('round')
                .strokeLinejoin('round')
                .strokeOpacity(0.7)

        String xml = circle.toXml()
        assertTrue(xml.contains('stroke="blue"'))
        assertTrue(xml.contains('stroke-width="2"'))
        assertTrue(xml.contains('stroke-dasharray="5 10"'))
        assertTrue(xml.contains('stroke-dashoffset="2"'))
        assertTrue(xml.contains('stroke-linecap="round"'))
        assertTrue(xml.contains('stroke-linejoin="round"'))
        assertTrue(xml.contains('stroke-opacity="0.7"'))
    }

    @Test
    void testMethodChaining() {
        Svg svg = new Svg(100, 100)
        Circle circle = svg.addCircle()

        Circle result = circle
                .cx(50)
                .cy(50)
                .r(40)
                .strokeDasharray('5 5')
                .strokeDashoffset(2)
                .strokeLinecap('round')
                .strokeLinejoin('round')
                .strokeMiterlimit(4)
                .strokeOpacity(0.5)
                .fillOpacity(0.8)
                .fillRule('evenodd')
                .mask('url(#mask)')
                .onClick('alert("clicked")')

        assertSame(circle, result)
    }

    @Test
    void testDashedLine() {
        // Real-world scenario: Create a dashed line
        Svg svg = new Svg(200, 100)
        svg.addLine(10, 50, 190, 50)
                .stroke('black')
                .strokeWidth(2)
                .strokeDasharray('10 5')
                .strokeLinecap('round')

        String xml = svg.toXml()
        assertTrue(xml.contains('stroke-dasharray="10 5"'))
        assertTrue(xml.contains('stroke-linecap="round"'))
    }

    @Test
    void testSelfIntersectingPathWithFillRule() {
        // Real-world scenario: Star shape with evenodd fill rule
        Svg svg = new Svg(100, 100)
        Path star = svg.addPath()
                .d('M50,10 L61,35 L90,35 L67,52 L78,77 L50,60 L22,77 L33,52 L10,35 L39,35 Z')
                .fill('gold')
                .stroke('black')
                .fillRule('evenodd')

        assertEquals('evenodd', star.fillRule)
        assertTrue(star.toXml().contains('fill-rule="evenodd"'))
    }

    @Test
    void testMaskedShape() {
        // Real-world scenario: Circle with mask reference
        Svg svg = new Svg(200, 200)
        svg.addCircle()
                .cx(100).cy(100).r(50)
                .fill('red')
                .mask('url(#circleMask)')

        String xml = svg.toXml()
        assertTrue(xml.contains('mask="url(#circleMask)"'))
    }
}
