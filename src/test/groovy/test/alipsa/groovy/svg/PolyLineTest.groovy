package test.alipsa.groovy.svg

import static org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Polyline

class PolyLineTest {

    @Test
    void testSimplePolyline() {
        Polyline polyline = new Polyline([0,40], [40,40], [40,80], [80,80], [80,120], [120,120], [120,160])
        .style("fill:yellow;stroke:red;stroke-width:4")
        assertEquals(
                '<polyline points="0,40 40,40 40,80 80,80 80,120 120,120 120,160" style="fill:yellow;stroke:red;stroke-width:4" />',
                polyline.toXml()
        )
    }
}
