package test.alipsa.groovy.svg

import se.alipsa.groovy.svg.Svg

import static org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class EllipseTest {

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
