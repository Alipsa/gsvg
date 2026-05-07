package test.alipsa.groovy.svg

import se.alipsa.groovy.svg.Svg

import static org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class EllipseTest {

    @Test
    void testEllipseRxRySetters() {
        Svg svg = new Svg(200, 200)
        def ellipse = svg.addEllipse().cx(100).cy(100).rx(80).ry(50)

        assertEquals('80', ellipse.rx)
        assertEquals('50', ellipse.ry)
        assertEquals('100', ellipse.cx)
        assertEquals('100', ellipse.cy)
    }

    @Test
    void testEllipseRxRyStringSetters() {
        Svg svg = new Svg(200, 200)
        def ellipse = svg.addEllipse().cx(100).cy(100).rx('80px').ry('50%')

        assertEquals('80px', ellipse.rx)
        assertEquals('50%', ellipse.ry)
    }

    @Test
    void testSimpleEllipse() {
        Svg svg = new Svg(100,100)
        svg.addEllipse(100, 50)
        .cx(200)
        .cy(80)
        .style("fill:yellow;stroke:green;stroke-width:3")

        assertEquals('<svg xmlns="http://www.w3.org/2000/svg" width="100" height="100">' +
            '<ellipse rx="100" ry="50" cx="200" cy="80" style="fill:yellow;stroke:green;stroke-width:3"/>' +
            '</svg>',
            svg.toXml())
    }
}
