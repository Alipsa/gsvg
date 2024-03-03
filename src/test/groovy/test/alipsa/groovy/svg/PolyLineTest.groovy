package test.alipsa.groovy.svg

import se.alipsa.groovy.svg.Svg

import static org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Polyline

class PolyLineTest {

    @Test
    void testSimplePolyline() {
        Svg svg = new Svg(100,100)
        Polyline polyline = svg.addPolyline([0,40], [40,40], [40,80], [80,80], [80,120], [120,120], [120,160])
        .style("fill:yellow;stroke:red;stroke-width:4")
        assertEquals(
                '<polyline xmlns="http://www.w3.org/2000/svg" points="0,40 40,40 40,80 80,80 80,120 120,120 120,160" style="fill:yellow;stroke:red;stroke-width:4"/>',
                polyline.toXml()
        )
    }
}
