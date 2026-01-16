package test.alipsa.groovy.svg

import se.alipsa.groovy.svg.Svg

import static org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Line

class LineTest {

    @Test
    void testSimpleLine() {
        Svg svg = new Svg(100,100)
        svg.addLine(0, 10, 100, 200)
        .style("stroke:red;stroke-width:2")
        assertEquals('<svg xmlns="http://www.w3.org/2000/svg" width="100" height="100">' +
            '<line x1="0" y1="10" x2="100" y2="200" style="stroke:red;stroke-width:2"/>' +
            '</svg>', svg.toXml())
    }
}
