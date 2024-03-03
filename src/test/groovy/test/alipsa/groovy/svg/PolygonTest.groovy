package test.alipsa.groovy.svg

import se.alipsa.groovy.svg.Svg

import static org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import se.alipsa.groovy.svg.Polygon

class PolygonTest {

    @Test
    void testSimplePolygon() {
        Svg svg = new Svg(100,100)
        Polygon polygon = svg.addPolygon()
        .points([200,10], [250,190], [150,190])
        .style("fill:lime;stroke:purple;stroke-width:3")

        assertEquals('<polygon xmlns="http://www.w3.org/2000/svg" points="200,10 250,190 150,190" style="fill:lime;stroke:purple;stroke-width:3"/>',
            polygon.toXml())

        assertEquals(
                '<polygon xmlns="http://www.w3.org/2000/svg" points="200,10 250,190 150,190"/>',
            svg.addPolygon([200,10], [250,190], [150,190]).toXml()
        )
    }
}
