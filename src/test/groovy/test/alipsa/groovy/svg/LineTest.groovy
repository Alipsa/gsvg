package test.alipsa.groovy.svg

import static org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Line

class LineTest {

    @Test
    void testSimpleLine() {
        Line line = new Line(0, 10, 100, 200)
        .style("stroke:red;stroke-width:2")
        assertEquals('<line x1="0" y1="10" x2="100" y2="200" style="stroke:red;stroke-width:2" />', line.toXml())
    }
}
